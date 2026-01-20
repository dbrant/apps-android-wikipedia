package org.wikipedia.wikilens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import org.wikipedia.R
import org.wikipedia.compose.theme.BaseTheme
import org.wikipedia.compose.theme.WikipediaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WikiLensScreen(
    state: WikiLensState,
    onPhotoClick: (PhotoItem) -> Unit,
    onFeatureClick: (ExtractedFeature) -> Unit,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WikiLens", color = WikipediaTheme.colors.primaryColor) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back_black_24dp),
                            contentDescription = "Back",
                            tint = WikipediaTheme.colors.primaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WikipediaTheme.colors.paperColor
                )
            )
        }
    ) { padding ->
        when (state) {
            is WikiLensState.Loading -> LoadingView(modifier = Modifier.padding(padding))
            is WikiLensState.PermissionDenied -> PermissionDeniedView(
                onRetry = onRetry,
                modifier = Modifier.padding(padding)
            )
            is WikiLensState.PhotosLoaded -> PhotoGridView(
                photos = state.photos,
                onPhotoClick = onPhotoClick,
                modifier = Modifier.padding(padding)
            )
            is WikiLensState.AnalyzingPhoto -> AnalyzingView(
                photo = state.photo,
                modifier = Modifier.padding(padding)
            )
            is WikiLensState.FeaturesExtracted -> FeaturesView(
                photo = state.photo,
                features = state.features,
                onFeatureClick = onFeatureClick,
                modifier = Modifier.padding(padding)
            )
            is WikiLensState.Error -> ErrorView(
                message = state.message,
                onRetry = onRetry,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(color = WikipediaTheme.colors.progressiveColor)
            Text(
                "Loading your photos...",
                color = WikipediaTheme.colors.primaryColor,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun PermissionDeniedView(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Photo Access Required",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = WikipediaTheme.colors.primaryColor
            )
            Text(
                "WikiLens needs access to your photos to identify interesting subjects and suggest Wikipedia articles.",
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
private fun PhotoGridView(
    photos: List<PhotoItem>,
    onPhotoClick: (PhotoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            "Select a photo to analyze",
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = WikipediaTheme.colors.primaryColor
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(photos) { photo ->
                AsyncImage(
                    model = photo.uri,
                    contentDescription = photo.displayName,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onPhotoClick(photo) },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
private fun AnalyzingView(
    photo: PhotoItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = photo.uri,
            contentDescription = photo.displayName,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = WikipediaTheme.colors.progressiveColor
        )
        
        Text(
            "Analyzing image...",
            fontSize = 16.sp,
            color = WikipediaTheme.colors.primaryColor
        )
    }
}

@Composable
private fun FeaturesView(
    photo: PhotoItem,
    features: List<ExtractedFeature>,
    onFeatureClick: (ExtractedFeature) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AsyncImage(
                model = photo.uri,
                contentDescription = photo.displayName,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
        
        item {
            Text(
                "Discovered Features",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = WikipediaTheme.colors.primaryColor,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        if (features.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = WikipediaTheme.colors.paperColor
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "No notable features detected",
                            fontSize = 16.sp,
                            color = WikipediaTheme.colors.secondaryColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(features) { feature ->
                FeatureCard(feature = feature, onClick = { onFeatureClick(feature) })
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
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = WikipediaTheme.colors.paperColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryIcon(category = feature.category)
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    feature.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = WikipediaTheme.colors.primaryColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    feature.description,
                    fontSize = 14.sp,
                    color = WikipediaTheme.colors.secondaryColor
                )
            }
            
            Icon(
                painter = painterResource(R.drawable.ic_search_white_24dp),
                contentDescription = "Read article",
                tint = WikipediaTheme.colors.progressiveColor
            )
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
            .size(48.dp)
            .background(
                WikipediaTheme.colors.paperColor,
                RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(emoji, fontSize = 24.sp)
    }
}

@Composable
private fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
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

@Preview(showBackground = true)
@Composable
private fun WikiLensScreenPreview() {
    BaseTheme {
        WikiLensScreen(
            state = WikiLensState.PhotosLoaded(emptyList()),
            onPhotoClick = {},
            onFeatureClick = {},
            onRetry = {},
            onBack = {}
        )
    }
}
