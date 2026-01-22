package org.wikipedia.wikilens

import org.wikipedia.page.PageTitle
import org.wikipedia.dataclient.WikiSite

data class ExtractedFeature(
    val title: String,
    val description: String,
    val confidence: Float,
    val category: FeatureCategory,
    val x: Float = 0.5f,  // Normalized position 0-1
    val y: Float = 0.5f   // Normalized position 0-1
) {
    fun toPageTitle(): PageTitle {
        return PageTitle(title, WikiSite.forLanguageCode("en"))
    }
}

enum class FeatureCategory {
    LANDMARK,
    ANIMAL,
    PLANT,
    OBJECT,
    PERSON,
    ARTWORK,
    OTHER
}

sealed class WikiLensState {
    object Initializing : WikiLensState()
    object PermissionDenied : WikiLensState()
    object CameraReady : WikiLensState()
    data class Analyzing(val features: List<ExtractedFeature> = emptyList()) : WikiLensState()
    data class Error(val message: String) : WikiLensState()
}
