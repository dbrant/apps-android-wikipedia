package org.wikipedia.auth

import android.app.PendingIntent
import android.content.Intent

interface OAuthClient {

    fun initialize()

    suspend fun getAccessToken(): String?

    suspend fun synchronizedRefreshAccessToken(): String

    fun isLoggedIn(): Boolean

    fun startLogin(pendingIntent: PendingIntent)

    suspend fun finishLogin(intent: Intent)

    fun startLogout(launchAction: (i: Intent) -> Unit)

    fun finishLogout()

    fun clearLoginState()

    fun expireAccessToken()

    fun expireRefreshToken()
}
