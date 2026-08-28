package org.wikipedia.auth

class OAuthConfiguration {

    lateinit var authority: String

    lateinit var clientId: String

    // Interstitial page that receives the login response
    lateinit var redirectUri: String

    // Interstitial page that receives the logout response
    lateinit var postLogoutRedirectUri: String

    // OAuth scopes being requested, for use when calling APIs after login
    lateinit var scope: String

    lateinit var userInfoEndpoint: String

    lateinit var customLogoutEndpoint: String

    lateinit var deepLinkBaseUrl: String
}
