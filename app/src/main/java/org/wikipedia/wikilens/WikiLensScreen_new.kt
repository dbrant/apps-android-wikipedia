package org.wikipedia.wikilens

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import org.wikipedia.R
import org.wikipedia.compose.theme.WikipediaTheme
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WikiLensScreen(
    state: WikiLensState,
    onFeatureClick: (ExtractedFeature) -> Unit,
    onStartAnalysis: (Bitmap) -> Unit,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is WikiLensState.Initializing -> LoadingView()
            is WikiLensState.PermissionDenied -> PermissionDeniedView(onRetry)
            is WikiLensState.CameraReady, is WikiLensState.Analyzing -> {
                CameraView(
                    features = if (state is WikiLensState.Analyzing) state.features else emptyList(),
                    onFeatureClick = onFeatureClick,
                    onFrameCaptured = onStartAnalysis
                )
            }
            is WikiLensState.Error -> ErrorView(state.message, onRetry)
        }
        
        // Top bar overlay
        TopAppBar(
            title = { Text("WikiLens", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back_black_24dp),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun CameraView(
    features: List<ExtractedFeature>,
    onFeatureClick: (ExtractedFeature) -> Unit,
    onFrameCaptured: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var previewView by remember { mutableStateOf<PreviewView?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera preview
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).also { preview ->
                    previewView = preview
                    val cameraProvider = cameraProviderFuture.get()
                    val previewUseCase = Preview.Builder().build().also {
                        it.setSurfaceProvider(preview.surfaceProvider)
                    }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also { analysis ->
                            analysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                                val bitmap = imageProxyToBitmap(imageProxy)
                                if (bitmap != null) {
                                    onFrameCaptured(bitmap)
                                }
                                imageProxy.close()
                            }
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            previewUseCase,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay detected features as floating cards
        features.forEach { feature ->
            FeatureOverlayCard(
                feature = feature,
                onClick = { onFeatureClick(feature) },
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset {
                        IntOffset(
                            ((feature.x - 0.5f) * 600).dp.roundToPx(),
                            ((feature.y - 0.5f) * 800).dp.roundToPx()
                        )
                    }
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraProviderFuture.get().unbindAll()
        }
    }
}

@Composable
private fun FeatureOverlayCard(
    feature: ExtractedFeature,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = WikipediaTheme.colors.paperColor.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
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
        Text(emoji, fontSize = 18.sp)
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WikipediaTheme.colors.paperColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(color = WikipediaTheme.colors.progressiveColor)
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
        modifier = Modifier
            .fillMaxSize()
            .background(WikipediaTheme.colors.paperColor),
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
                "WikiLens needs camera access to identify objects in real-time and suggest Wikipedia articles.",
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
        modifier = Modifier
            .fillMaxSize()
            .background(WikipediaTheme.colors.paperColor),
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

private fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {
    val buffer = imageProxy.planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}
