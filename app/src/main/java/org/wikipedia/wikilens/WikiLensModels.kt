package org.wikipedia.wikilens

import android.net.Uri
import org.wikipedia.page.PageTitle
import org.wikipedia.dataclient.WikiSite

data class PhotoItem(
    val uri: Uri,
    val dateTaken: Long,
    val displayName: String
)

data class ExtractedFeature(
    val title: String,
    val description: String,
    val confidence: Float,
    val category: FeatureCategory
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
    object Loading : WikiLensState()
    object PermissionDenied : WikiLensState()
    data class PhotosLoaded(val photos: List<PhotoItem>) : WikiLensState()
    data class AnalyzingPhoto(val photo: PhotoItem, val allPhotos: List<PhotoItem>) : WikiLensState()
    data class FeaturesExtracted(
        val photo: PhotoItem,
        val features: List<ExtractedFeature>,
        val allPhotos: List<PhotoItem>
    ) : WikiLensState()
    data class Error(val message: String) : WikiLensState()
}
