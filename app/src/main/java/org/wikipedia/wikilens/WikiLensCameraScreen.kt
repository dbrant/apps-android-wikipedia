package org.wikipedia.wikilens

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import org.wikipedia.R
import org.wikipedia.compose.theme.WikipediaTheme
import java.nio.ByteBuffer
import java.util.concurrent.Executors
import kotlin.math.roundToInt

@Composable
fun WikiLensCameraScreen(
    state: WikiLensState,
    onFeatureClick: (ExtractedFeature) -> Unit,
    onStartAnalysis: (Bitmap) -> Unit,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is WikiLensState.Initializing -> {
                LoadingView()
            }
            is WikiLensState.PermissionDenied -> {
                PermissionDeniedView(onRetry = onRetry)
            }
            is WikiLensState.CameraReady, is WikiLensState.Analyzing -> {
                CameraView(
                    onImageAnalyzed = onStartAnalysis,
                    onBack = onBack
                )
                
                if (state is WikiLensState.Analyzing) {
                    FeatureOverlay(
                        features = state.features,
                        onFeatureClick = onFeatureClick
                    )
                }
            }
            is WikiLensState.Error -> {
                ErrorView(message = state.message, onRetry = onRetry)
            }
        }
    }
}

@Composable
private fun CameraView(
    onImageAnalyzed: (Bitmap) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var previewView by remember { mutableStateOf<PreviewView?>(null) }
    
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PreviewView(ctx).also {
                    previewView = it
                }
            }
        )
        
        // Back button overlay
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(
                    WikipediaTheme.colors.paperColor.copy(alpha = 0.7f),
                    CircleShape
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back_black_24dp),
                contentDescription = "Back",
                tint = WikipediaTheme.colors.primaryColor
            )
        }
        
        // Info text
        Text(
            text = "Point camera at objects to discover Wikipedia articles",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                .background(
                    WikipediaTheme.colors.paperColor.copy(alpha = 0.9f),
                    RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            color = WikipediaTheme.colors.primaryColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
    
    LaunchedEffect(previewView, cameraProviderFuture) {
        previewView?.let { view ->
            val cameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = view.surfaceProvider
            }
            
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                        processImageProxy(imageProxy, onImageAnalyzed)
                        imageProxy.close()
                    }
                }
            
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
                // Handle error
            }
        }
    }
    
    DisposableEffect(lifecycleOwner) {
        onDispose {
            cameraProviderFuture.get()?.unbindAll()
        }
    }
}

private fun processImageProxy(imageProxy: ImageProxy, onImageAnalyzed: (Bitmap) -> Unit) {
    try {
        // Convert ImageProxy to Bitmap using YUV format
        val yBuffer = imageProxy.planes[0].buffer
        val uBuffer = imageProxy.planes[1].buffer
        val vBuffer = imageProxy.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = android.graphics.YuvImage(nv21, android.graphics.ImageFormat.NV21, imageProxy.width, imageProxy.height, null)
        val out = java.io.ByteArrayOutputStream()
        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, imageProxy.width, imageProxy.height), 50, out)
        val imageBytes = out.toByteArray()
        val bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        
        bitmap?.let { onImageAnalyzed(it) }
    } catch (e: Exception) {
        // Ignore errors during frame processing
    }
}

@Composable
private fun FeatureOverlay(
    features: List<ExtractedFeature>,
    onFeatureClick: (ExtractedFeature) -> Unit
) {
    // Display features as a vertical list at the bottom of the screen
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 100.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            features.forEach { feature ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                ) {
                    FeatureCard(
                        feature = feature,
                        onClick = { onFeatureClick(feature) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    feature: ExtractedFeature,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = WikipediaTheme.colors.paperColor.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryIcon(category = feature.category)
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column {
                Text(
                    feature.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = WikipediaTheme.colors.primaryColor
                )
                Text(
                    feature.description,
                    fontSize = 11.sp,
                    color = WikipediaTheme.colors.secondaryColor
                )
            }
        }
    }
}

@Composable
private fun CategoryIcon(category: FeatureCategory) {
    val emoji = when (category) {
        FeatureCategory.LANDMARK -> "🏛️"
        FeatureCategory.ANIMAL -> "🦁"
        FeatureCategory.PLANT -> "🌿"
        FeatureCategory.OBJECT -> "📦"
        FeatureCategory.PERSON -> "👤"
        FeatureCategory.ARTWORK -> "🎨"
        FeatureCategory.OTHER -> "🔍"
    }
    
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                WikipediaTheme.colors.paperColor,
                RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(emoji, fontSize = 16.sp)
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Initializing camera...",
                color = WikipediaTheme.colors.primaryColor,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun PermissionDeniedView(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Camera Access Required",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = WikipediaTheme.colors.primaryColor
            )
            Text(
                "WikiLens needs camera access to identify objects and suggest Wikipedia articles in real-time.",
                textAlign = TextAlign.Center,
                color = WikipediaTheme.colors.secondaryColor
            )
            Button(onClick = onRetry) {
                Text("Grant Permission")
            }
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Error",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = WikipediaTheme.colors.primaryColor
            )
            Text(
                message,
                textAlign = TextAlign.Center,
                color = WikipediaTheme.colors.secondaryColor
            )
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}
