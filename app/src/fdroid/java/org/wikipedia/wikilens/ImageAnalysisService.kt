package org.wikipedia.wikilens

import android.content.Context
import android.net.Uri

/**
 * Stub implementation for F-Droid build that doesn't include Google ML Kit.
 * This version returns empty results.
 */
object ImageAnalysisService {

    suspend fun analyzeImage(context: Context, photoUri: Uri): List<ExtractedFeature> {
        // F-Droid build: ML Kit is not available
        // Return empty list or could show a message that this feature requires Google Play Services
        return emptyList()
    }
}
