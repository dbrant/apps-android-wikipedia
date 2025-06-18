package org.wikipedia.auth

import com.google.gson.annotations.SerializedName

/*
 * A holder for configuration settings
 */
class Configuration {

    // OAuth plumbing properties
    @SerializedName("oauth")
    lateinit var oauth: OAuthConfiguration
}
