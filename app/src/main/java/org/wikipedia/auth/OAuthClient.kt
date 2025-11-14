package org.wikipedia.auth

import android.app.PendingIntent
import android.content.Intent

interface OAuthClient {

    // Startup initialization
    fun initialize()

    // Try to get an access token
    suspend fun getAccessToken(): String?

    // Try to refresh an access token
    suspend fun synchronizedRefreshAccessToken(): String

    // Return true if there are tokens
    fun isLoggedIn(): Boolean

    // Start a login redirect
    fun startLogin(pendingIntent: PendingIntent)

    // Complete a login
    suspend fun finishLogin(intent: Intent)

    // Start a logout redirect
    fun startLogout(launchAction: (i: Intent) -> Unit)

    // Complete a logout
    fun finishLogout()

    // Allow the app to clear its login state after certain errors
    fun clearLoginState()

    // For testing, make the access token act expired
    fun expireAccessToken()

    // For testing, make the refresh token act expired
    fun expireRefreshToken()
}
