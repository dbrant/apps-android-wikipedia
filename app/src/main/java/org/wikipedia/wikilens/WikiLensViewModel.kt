package org.wikipedia.wikilens

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.wikipedia.util.log.L

class WikiLensViewModel : ViewModel() {

    private val _state = MutableStateFlow<WikiLensState>(WikiLensState.Initializing)
    val state: StateFlow<WikiLensState> = _state.asStateFlow()

    private var analysisJob: Job? = null
    private val analysisThrottleMs = 1500L // Analyze every 1.5 seconds
    private val maxFeaturesDisplayed = 5
    
    private var currentFeatures: List<ExtractedFeature> = emptyList()

    fun onCameraPermissionGranted() {
        _state.value = WikiLensState.CameraReady
    }

    fun analyzeFrame(bitmap: Bitmap) {
        // Cancel previous analysis if still running
        analysisJob?.cancel()

        analysisJob = viewModelScope.launch {
            try {
                val context = org.wikipedia.WikipediaApp.instance
                val newFeatures = ImageAnalysisService.analyzeBitmap(context, bitmap)
                
                // Only update if we have new features, otherwise keep showing the old ones
                if (newFeatures.isNotEmpty()) {
                    currentFeatures = newFeatures.take(maxFeaturesDisplayed)
                    _state.value = WikiLensState.Analyzing(currentFeatures)
                    
                    currentFeatures.forEach {
                        L.d("${it.title} - ${it.confidence}")
                    }
                } else {
                    // Keep showing previous features if no new ones found
                    _state.value = WikiLensState.Analyzing(currentFeatures)
                }
                
                // Throttle next analysis
                delay(analysisThrottleMs)
            } catch (e: Exception) {
                L.e("WikiLens analysis error", e)
                // Keep showing previous features on error
                _state.value = WikiLensState.Analyzing(currentFeatures)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        analysisJob?.cancel()
    }
}
