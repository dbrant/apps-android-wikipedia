package org.wikipedia.auth

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.NoClientAuthentication
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import net.openid.appauth.TokenResponse
import net.openid.appauth.browser.BrowserAllowList
import net.openid.appauth.browser.BrowserMatcher
import net.openid.appauth.browser.VersionedBrowserMatcher
import org.wikipedia.auth.logout.LogoutUrlBuilder
import org.wikipedia.auth.logout.StandardLogoutUrlBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import androidx.core.net.toUri
import org.wikipedia.WikipediaApp

class OAuthClientImpl(
    private val configuration: OAuthConfiguration,
    private val applicationContext: Context
) : OAuthClient {

    private var metadata: AuthorizationServiceConfiguration? = null
    private var loginAuthService: AuthorizationService? = null
    private var logoutAuthService: AuthorizationService? = null
    private val tokenStorage = TokenStorage(this.applicationContext)

    /*
     * One time initialization on application startup
     */
    override fun initialize() {

        // Load OpenID Connect metadata
        this.getMetadata()

        // Load tokens from storage
        this.tokenStorage.loadTokens()
    }

    /*
     * Try to get an access token, which most commonly involves returning the current one
     */
    override suspend fun getAccessToken(): String? {

        // See if there is a token in storage
        val accessToken = this.tokenStorage.getTokens().accessToken
        if (!accessToken.isNullOrBlank()) {
            return accessToken
        }

        // Indicate no access token
        return null
    }

    /*
     * Try to refresh an access token
     */
    override suspend fun synchronizedRefreshAccessToken(): String {

        val refreshToken = this.tokenStorage.getTokens().refreshToken
        if (!refreshToken.isNullOrBlank()) {

            //this.concurrencyHandler.execute(this::performRefreshTokenGrant)
            performRefreshTokenGrant()

            // Return the token on success
            val accessToken = this.tokenStorage.getTokens().accessToken
            if (!accessToken.isNullOrBlank()) {
                return accessToken
            }
        }

        // Otherwise abort the API call via a known exception
        throw Exception("Login required.")
    }

    // Return true if there are tokens
    override fun isLoggedIn(): Boolean {
        return !tokenStorage.getTokens().accessToken.isNullOrEmpty()
    }

    /*
     * Do the work to perform an authorization redirect
     */
    override fun startLogin(pendingIntent: PendingIntent) {
        val authService = AuthorizationService(this.applicationContext, this.getBrowserConfiguration())
        this.loginAuthService = authService

        // Create the AppAuth request object and use Authorization Code Flow (PKCE)
        // If required, call builder.setAdditionalParameters to supply details such as acr_values
        val builder = AuthorizationRequest.Builder(
            this.metadata!!,
            this.configuration.clientId,
            ResponseTypeValues.CODE,
            this.configuration.redirectUri.toUri()
        )
            .setScope(this.configuration.scope)
        val request = builder.build()

        authService.performAuthorizationRequest(request, pendingIntent)
    }

    /*
     * When a login redirect completes, process the login response here
     */
    override suspend fun finishLogin(intent: Intent) {
        // Get the response details
        val authorizationResponse = AuthorizationResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)

        // Free custom tab resources after a login
        // https://github.com/openid/AppAuth-Android/issues/91
        this.loginAuthService?.dispose()
        this.loginAuthService = null

        when {
            ex != null -> {
                // Handle the case where the user closes the Chrome Custom Tab rather than logging in
                if (ex.type == AuthorizationException.TYPE_GENERAL_ERROR &&
                    ex.code == AuthorizationException.GeneralErrors.USER_CANCELED_AUTH_FLOW.code
                ) {
                    throw Exception("Login cancelled from redirect.")
                }
                // Translate AppAuth errors to the display format
                throw Exception("Login operation failed.")
            }
            authorizationResponse != null -> {
                this.exchangeAuthorizationCode(authorizationResponse)
            }
        }
    }

    /*
     * Remove local state and start the logout redirect to remove the OAuth session cookie
     */
    override fun startLogout(launchAction: (i: Intent) -> Unit) {

        // First force removal of tokens from storage
        val tokens = this.tokenStorage.getTokens()
        val idToken = tokens.idToken
        this.clearLoginState()

        // Fail if there is no id token
        if (idToken == null) {

            val message = "Logout is not possible because tokens have already been removed"
            throw IllegalStateException(message)
        }

        // Create an object to manage logout and use it to form the end session request
        val logoutUrlBuilder = this.createLogoutUrlBuilder()
        val logoutUrl = logoutUrlBuilder.getEndSessionRequestUrl(
            this.metadata!!,
            this.configuration.postLogoutRedirectUri,
            idToken
        )

        // Launch the intent to sign the user out
        launchAction(this.getLogoutIntent(logoutUrl))
    }

    /*
     * Free resources after a logout
     * https://github.com/openid/AppAuth-Android/issues/91
     */
    override fun finishLogout() {

        if (this.logoutAuthService != null) {
            this.logoutAuthService?.dispose()
            this.logoutAuthService = null
        }
    }

    /*
     * Allow the login state to be cleared when required
     */
    override fun clearLoginState() {
        this.tokenStorage.removeTokens()
    }

    /*
     * For testing, make the access token act like it is expired
     */
    override fun expireAccessToken() {
        this.tokenStorage.expireAccessToken()
    }

    /*
     * For testing, make the refresh token act like it is expired
     */
    override fun expireRefreshToken() {
        this.tokenStorage.expireRefreshToken()
    }

    /*
     * Get metadata and convert the callback to a suspendable function
     */
    private fun getMetadata() {
        metadata = AuthorizationServiceConfiguration(
            "https://meta.wikimedia.org/w/rest.php/oauth2/authorize".toUri(), // authorization endpoint
            "https://meta.wikimedia.org/w/rest.php/oauth2/access_token".toUri() // token endpoint
            )
    }

    /*
     * When a login succeeds, exchange the authorization code for tokens
     */
    private suspend fun exchangeAuthorizationCode(authResponse: AuthorizationResponse) {
        return suspendCoroutine { continuation ->
            val callback = AuthorizationService.TokenResponseCallback { tokenResponse, ex ->
                when {
                    // Translate AppAuth errors to the display format
                    ex != null -> {
                        continuation.resumeWithException(ex)
                    }
                    tokenResponse == null -> {
                        val empty = RuntimeException("Authorization code grant returned an empty response")
                        continuation.resumeWithException(empty)
                    }
                    else -> {
                        this.saveTokens(tokenResponse)
                        continuation.resume(Unit)
                    }
                }
            }
            val tokenRequest = authResponse.createTokenExchangeRequest()
            val authService = AuthorizationService(this.applicationContext)
            authService.performTokenRequest(tokenRequest, NoClientAuthentication.INSTANCE, callback)
        }
    }

    /*
     * Do the work of refreshing an access token
     */
    private suspend fun performRefreshTokenGrant() {
        // Check we have a refresh token
        val refreshToken = this.tokenStorage.getTokens().refreshToken
        if (refreshToken.isNullOrBlank()) {
            return
        }
        return suspendCoroutine { continuation ->
            // Define a callback to handle the result of the refresh token grant
            val callback =
                AuthorizationService.TokenResponseCallback { tokenResponse, ex ->

                    when {
                        // Translate AppAuth errors to the display format
                        ex != null -> {

                            // If we get an invalid_grant error it means the refresh token has expired
                            if (ex.type == AuthorizationException.TYPE_OAUTH_TOKEN_ERROR &&
                                ex.code == AuthorizationException.TokenRequestErrors.INVALID_GRANT.code
                            ) {
                                // Remove tokens and indicate success, since this is an expected error
                                // The caller will throw a login required error to redirect the user to login again
                                this.clearLoginState()
                                continuation.resume(Unit)

                            } else {
                                // Process real errors
                                continuation.resumeWithException(ex)
                            }
                        }

                        // Sanity check
                        tokenResponse == null -> {
                            val error = RuntimeException("Refresh token grant returned an empty response")
                            continuation.resumeWithException(error)
                        }

                        // Process the response by saving tokens to secure storage
                        else -> {
                            this.saveTokens(tokenResponse)
                            continuation.resume(Unit)
                        }
                    }
                }

            // Create the refresh token grant request
            val tokenRequest = TokenRequest.Builder(
                this.metadata!!,
                this.configuration.clientId
            )
                .setGrantType(GrantTypeValues.REFRESH_TOKEN)
                .setRefreshToken(refreshToken)
                .build()

            // Trigger the request
            val authService = AuthorizationService(this.applicationContext)
            authService.performTokenRequest(tokenRequest, callback)
        }
    }

    /*
     * Common handling of token responses, for authorization code grant and refresh token messages
     */
    private fun saveTokens(tokenResponse: TokenResponse) {

        // Create token data from the response
        val tokenData = TokenData()
        tokenData.accessToken = tokenResponse.accessToken
        tokenData.refreshToken = tokenResponse.refreshToken
        tokenData.idToken = tokenResponse.idToken

        // The response may have blank values for these tokens
        if (tokenData.refreshToken.isNullOrBlank() || tokenData.idToken.isNullOrBlank()) {

            // See if there is any existing token data
            val oldTokenData = this.tokenStorage.getTokens()
            if (oldTokenData != null) {

                // Maintain the existing refresh token unless we received a new 'rolling' refresh token
                if (tokenData.refreshToken.isNullOrBlank()) {
                    tokenData.refreshToken = oldTokenData.refreshToken
                }

                // Maintain the existing id token if required, which may be needed for logout
                if (tokenData.idToken.isNullOrBlank()) {
                    tokenData.idToken = oldTokenData.idToken
                }
            }
        }

        this.tokenStorage.saveTokens(tokenData)
    }

    private fun createLogoutUrlBuilder(): LogoutUrlBuilder {
        return StandardLogoutUrlBuilder(this.configuration, this.metadata!!)
    }

    /*
     * Create the intent for the logout redirect
     */
    private fun getLogoutIntent(logoutUrl: String): Intent {

        // Create the auth service and set the browser to use
        val authService = AuthorizationService(this.applicationContext, this.getBrowserConfiguration())
        this.logoutAuthService = authService

        if (this.getBrowser() == VersionedBrowserMatcher.CHROME_CUSTOM_TAB) {

            // Start a logout intent on a Chrome Custom tab
            val customTabsIntent = authService.customTabManager.createTabBuilder().build()
            val logoutIntent = customTabsIntent.intent
            logoutIntent.setPackage(authService.browserDescriptor.packageName)
            logoutIntent.data = logoutUrl.toUri()
            return logoutIntent

        } else {

            // Start a logout intent in the Chrome browser
            val logoutIntent = Intent(Intent.ACTION_VIEW)
            logoutIntent.data = logoutUrl.toUri()
            return logoutIntent
        }
    }

    /*
     * Control the browser to use for login and logout redirects
     */
    private fun getBrowserConfiguration(): AppAuthConfiguration {

        return AppAuthConfiguration.Builder()
            .setBrowserMatcher(BrowserAllowList(this.getBrowser()))
            .build()
    }

    /*
     * Android emulators on levels 31 and 32 do not reliably return to the app after custom tab redirects
     * Work around this bug using the Chrome system browser until these emulator issues stabilize
     */
    private fun getBrowser(): BrowserMatcher {

        if (this.isEmulator() && (Build.VERSION.SDK_INT == 31 || Build.VERSION.SDK_INT == 32)) {
            return VersionedBrowserMatcher.CHROME_BROWSER
        }

        return VersionedBrowserMatcher.CHROME_CUSTOM_TAB
    }

    /*
     * Basic detection on whether the app is running on an emulator
     */
    private fun isEmulator(): Boolean {

        val model = Build.MODEL.lowercase()
        val manufacturer = Build.MANUFACTURER.lowercase()
        return model.contains("emulator") ||
            (model.startsWith("sdk_gphone") && manufacturer.contains("google"))

    }

    companion object {
        private var _instance: OAuthClient? = null
        val instance: OAuthClient get() {
            if (_instance == null) {
                initialize(WikipediaApp.instance)
            }
            return _instance!!
        }

        private fun initialize(context: Context) {
            val config = OAuthConfiguration()
            config.scope = "" //""openid profile https://wikipedia.org/"
            config.clientId = "50ad79ffa34f64853c96b729e4aa5d8c"
            config.redirectUri = "wikipedia://oauth/callback"
            config.authority = "https://meta.wikimedia.org/w/rest.php/oauth2/authorize"
            config.postLogoutRedirectUri = ""
            config.customLogoutEndpoint = ""
            config.deepLinkBaseUrl = "wikipedia://"
            config.userInfoEndpoint = "https://meta.wikimedia.org/w/rest.php/oauth2/resource/profile"

            _instance = OAuthClientImpl(config, context)
            _instance?.initialize()
        }
    }
}
