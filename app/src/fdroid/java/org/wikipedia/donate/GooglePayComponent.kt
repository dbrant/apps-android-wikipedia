package org.wikipedia.donate

object GooglePayComponent {
    suspend fun isGooglePayAvailable(): Boolean {
        return false
    }

    fun onGooglePayButtonClicked(activity: Activity) {
    }
}
