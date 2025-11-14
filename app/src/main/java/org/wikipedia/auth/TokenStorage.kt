package org.wikipedia.auth

import android.content.Context
import org.wikipedia.settings.Prefs

class TokenStorage(private val context: Context) {

    private val tokenData = TokenData()

    fun loadTokens() {
        tokenData.accessToken = Prefs.oauthAccessToken
        tokenData.refreshToken = Prefs.oauthRefreshToken
        tokenData.idToken = Prefs.oauthIdToken
    }

    fun getTokens(): TokenData {
        return this.tokenData
    }

    fun saveTokens(newTokenData: TokenData) {
        tokenData.accessToken = newTokenData.accessToken
        tokenData.refreshToken = newTokenData.refreshToken
        tokenData.idToken = newTokenData.idToken
        this.saveTokenData()
    }

    fun removeTokens() {
        this.tokenData.accessToken = null
        this.tokenData.refreshToken = null
        this.tokenData.idToken = null
        this.saveTokenData()
    }

    fun expireAccessToken() {
        this.tokenData.accessToken = "${this.tokenData.accessToken}x"
        this.saveTokenData()
    }
    fun expireRefreshToken() {
        this.tokenData.accessToken = "${this.tokenData.accessToken}x"
        this.tokenData.refreshToken = "${this.tokenData.refreshToken}x"
        this.saveTokenData()
    }

    private fun saveTokenData() {
        Prefs.oauthAccessToken = this.tokenData.accessToken
        Prefs.oauthRefreshToken = this.tokenData.refreshToken
        Prefs.oauthIdToken = this.tokenData.idToken
    }
}
