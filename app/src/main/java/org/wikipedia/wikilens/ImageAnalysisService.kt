package org.wikipedia.wikilens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.protobuf.ByteString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.wikipedia.util.log.L
import java.io.ByteArrayOutputStream

/**
 * Image analysis service using Google Cloud Vision API for high-quality image labeling.
 * Falls back to on-device ML Kit processing if cloud API is unavailable.
 * 
 * Cloud Vision API provides significantly better accuracy with:
 * - Landmark detection
 * - Object and product detection
 * - Logo detection
 * - Web entity detection
 * - More detailed label annotations
 */
object ImageAnalysisService {

    private const val CONFIDENCE_THRESHOLD = 0.5f
    private const val MAX_RESULTS = 15
    private const val USE_CLOUD_VISION = false

    suspend fun analyzeBitmap(context: Context, bitmap: Bitmap): List<ExtractedFeature> {
        return try {
            if (USE_CLOUD_VISION) {
                analyzeWithCloudVision(bitmap)
            } else {
                analyzeOnDevice(context, bitmap)
            }
        } catch (e: Exception) {
            // Fallback to on-device if cloud fails
            try {
                analyzeOnDevice(context, bitmap)
            } catch (e2: Exception) {
                emptyList()
            }
        }
    }

    suspend fun analyzeImage(context: Context, photoUri: Uri): List<ExtractedFeature> {
        val bitmap = loadBitmap(context, photoUri) ?: return emptyList()
        return analyzeBitmap(context, bitmap)
    }

    private suspend fun analyzeWithCloudVision(bitmap: Bitmap): List<ExtractedFeature> = withContext(Dispatchers.IO) {
        val features = mutableListOf<ExtractedFeature>()
        
        try {
            ImageAnnotatorClient.create().use { vision ->
                val imgBytes = bitmapToByteString(bitmap)
                val img = Image.newBuilder().setContent(imgBytes).build()
                
                val featList = listOf(
                    Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).setMaxResults(10).build(),
                    Feature.newBuilder().setType(Feature.Type.LANDMARK_DETECTION).setMaxResults(5).build(),
                    Feature.newBuilder().setType(Feature.Type.LOGO_DETECTION).setMaxResults(5).build(),
                    Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).setMaxResults(5).build(),
                    Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).setMaxResults(5).build()
                )
                
                val request = AnnotateImageRequest.newBuilder()
                    .addAllFeatures(featList)
                    .setImage(img)
                    .build()
                
                val response = vision.batchAnnotateImages(listOf(request))
                val annotation = response.responsesList.firstOrNull()
                
                annotation?.let { ann ->
                    // Add landmark detections
                    ann.landmarkAnnotationsList.forEach { landmark ->
                        if (landmark.score >= CONFIDENCE_THRESHOLD) {
                            features.add(ExtractedFeature(
                                title = landmark.description,
                                description = "Landmark - ${(landmark.score * 100).toInt()}% confidence",
                                confidence = landmark.score,
                                category = FeatureCategory.LANDMARK
                            ))
                        }
                    }
                    
                    // Add logo detections
                    ann.logoAnnotationsList.forEach { logo ->
                        if (logo.score >= CONFIDENCE_THRESHOLD) {
                            features.add(ExtractedFeature(
                                title = logo.description,
                                description = "Logo - ${(logo.score * 100).toInt()}% confidence",
                                confidence = logo.score,
                                category = FeatureCategory.OTHER
                            ))
                        }
                    }
                    
                    // Add object localizations
                    ann.localizedObjectAnnotationsList.forEach { obj ->
                        if (obj.score >= CONFIDENCE_THRESHOLD) {
                            features.add(ExtractedFeature(
                                title = formatLabelText(obj.name),
                                description = "Object - ${(obj.score * 100).toInt()}% confidence",
                                confidence = obj.score,
                                category = categorizeLabel(obj.name)
                            ))
                        }
                    }
                    
                    // Add web entities (things identified from web matches)
                    ann.webDetection?.webEntitiesList?.forEach { entity ->
                        if (entity.score >= CONFIDENCE_THRESHOLD && entity.description.isNotEmpty()) {
                            features.add(ExtractedFeature(
                                title = entity.description,
                                description = "Web entity - ${(entity.score * 100).toInt()}% confidence",
                                confidence = entity.score,
                                category = categorizeLabel(entity.description)
                            ))
                        }
                    }
                    
                    // Add label annotations
                    ann.labelAnnotationsList.forEach { label ->
                        if (label.score >= CONFIDENCE_THRESHOLD) {
                            features.add(ExtractedFeature(
                                title = formatLabelText(label.description),
                                description = "${(label.score * 100).toInt()}% confidence",
                                confidence = label.score,
                                category = categorizeLabel(label.description)
                            ))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // If Cloud Vision fails, return empty and let caller fallback to on-device
            L.e(e)
            throw e
        }
        
        // Sort by confidence and remove duplicates
        features
            .sortedByDescending { it.confidence }
            .distinctBy { it.title.lowercase() }
            .take(MAX_RESULTS)
    }

    private fun bitmapToByteString(bitmap: Bitmap): ByteString {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        return ByteString.copyFrom(outputStream.toByteArray())
    }

    private suspend fun analyzeOnDevice(context: Context, bitmap: Bitmap): List<ExtractedFeature> {
        val inputImage = InputImage.fromBitmap(bitmap, 0)
        val options = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(CONFIDENCE_THRESHOLD)
            .build()
        
        val labeler = ImageLabeling.getClient(options)
        
        return try {
            val labels = labeler.process(inputImage).await()
            
            labels.take(MAX_RESULTS).map { label ->
                ExtractedFeature(
                    title = formatLabelText(label.text),
                    description = "Confidence: ${(label.confidence * 100).toInt()}%",
                    confidence = label.confidence,
                    category = categorizeLabel(label.text)
                )
            }
        } finally {
            labeler.close()
        }
    }

    private fun formatLabelText(text: String): String {
        // Capitalize first letter of each word for better Wikipedia article matching
        return text.split(" ", "_").joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
    }

    private fun loadBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun categorizeLabel(label: String): FeatureCategory {
        val lowerLabel = label.lowercase()
        
        return when {
            lowerLabel.contains("building") || 
            lowerLabel.contains("landmark") || 
            lowerLabel.contains("monument") ||
            lowerLabel.contains("architecture") -> FeatureCategory.LANDMARK
            
            lowerLabel.contains("animal") || 
            lowerLabel.contains("bird") || 
            lowerLabel.contains("mammal") ||
            lowerLabel.contains("fish") ||
            lowerLabel.contains("insect") -> FeatureCategory.ANIMAL
            
            lowerLabel.contains("plant") || 
            lowerLabel.contains("flower") || 
            lowerLabel.contains("tree") ||
            lowerLabel.contains("leaf") -> FeatureCategory.PLANT
            
            lowerLabel.contains("art") || 
            lowerLabel.contains("painting") || 
            lowerLabel.contains("sculpture") -> FeatureCategory.ARTWORK
            
            lowerLabel.contains("person") || 
            lowerLabel.contains("human") ||
            lowerLabel.contains("face") -> FeatureCategory.PERSON
            
            else -> FeatureCategory.OTHER
        }
    }
}
