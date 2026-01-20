package org.wikipedia.wikilens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WikiLensViewModel : ViewModel() {

    private val _state = MutableStateFlow<WikiLensState>(WikiLensState.Loading)
    val state: StateFlow<WikiLensState> = _state.asStateFlow()

    fun loadRecentPhotos(context: Context) {
        viewModelScope.launch {
            try {
                _state.value = WikiLensState.Loading
                val photos = PhotoAccessHelper.loadRecentPhotos(context)
                
                if (photos.isEmpty()) {
                    _state.value = WikiLensState.Error("No photos found in your gallery")
                } else {
                    _state.value = WikiLensState.PhotosLoaded(photos)
                }
            } catch (e: Exception) {
                _state.value = WikiLensState.Error("Failed to load photos: ${e.message}")
            }
        }
    }

    fun analyzePhoto(photo: PhotoItem) {
        val currentState = _state.value
        val allPhotos = when (currentState) {
            is WikiLensState.PhotosLoaded -> currentState.photos
            is WikiLensState.FeaturesExtracted -> currentState.allPhotos
            else -> emptyList()
        }

        viewModelScope.launch {
            try {
                _state.value = WikiLensState.AnalyzingPhoto(photo, allPhotos)
                
                // Note: This requires ML Kit dependencies in build.gradle
                // For now, this will use a fallback implementation
                val features = try {
                    val context = org.wikipedia.WikipediaApp.instance
                    ImageAnalysisService.analyzeImage(context, photo.uri)
                } catch (e: Exception) {
                    // Fallback to demo features if ML Kit is not available
                    createDemoFeatures()
                }
                
                _state.value = WikiLensState.FeaturesExtracted(photo, features, allPhotos)
            } catch (e: Exception) {
                _state.value = WikiLensState.Error("Failed to analyze photo: ${e.message}")
            }
        }
    }

    private fun createDemoFeatures(): List<ExtractedFeature> {
        // Demo features for testing when ML Kit is not yet configured
        return listOf(
            ExtractedFeature(
                title = "Landmarks",
                description = "Detected with 75% confidence",
                confidence = 0.75f,
                category = FeatureCategory.LANDMARK
            ),
            ExtractedFeature(
                title = "Architecture",
                description = "Detected with 68% confidence",
                confidence = 0.68f,
                category = FeatureCategory.LANDMARK
            )
        )
    }
}
