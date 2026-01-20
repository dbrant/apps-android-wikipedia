# WikiLens Feature

WikiLens is a new feature that uses AI-powered image recognition to help users discover Wikipedia articles related to their photos.

## Overview

WikiLens allows users to:
1. Grant permission to access their photo gallery
2. Select photos from their recent images
3. Automatically analyze photos using Google ML Kit's on-device image labeling
4. View discovered features (landmarks, animals, plants, objects, etc.)
5. Navigate directly to Wikipedia articles about identified subjects

## Architecture

### Components

- **WikiLensActivity**: Main entry point using Jetpack Compose
- **WikiLensViewModel**: Manages state and coordinates photo loading and analysis
- **WikiLensScreen**: Compose UI implementation with multiple states
- **PhotoAccessHelper**: Handles MediaStore queries for recent photos
- **ImageAnalysisService**: Uses Google ML Kit for on-device image recognition
- **WikiLensModels**: Data classes for photos, features, and state management

### Key Features

1. **Privacy-First**: Uses Google ML Kit's on-device processing, no images sent to external servers
2. **Modern UI**: Built entirely with Jetpack Compose
3. **Seamless Integration**: Accessible from the main navigation menu
4. **Permission Handling**: Proper Android 13+ photo permission flow

## File Structure

```
app/src/main/java/org/wikipedia/wikilens/
├── WikiLensActivity.kt         # Main Activity with Compose integration
├── WikiLensViewModel.kt        # State management and business logic
├── WikiLensScreen.kt           # Compose UI components
├── WikiLensModels.kt           # Data models and state classes
├── PhotoAccessHelper.kt        # MediaStore photo access
└── ImageAnalysisService.kt     # ML Kit image recognition

app/src/main/res/
├── layout/view_main_drawer.xml # Added WikiLens menu item
├── values/strings.xml          # WikiLens strings and permissions
└── drawable/ic_camera_24px.xml # Camera icon for menu
```

## Usage

1. Open the Wikipedia app
2. Tap the menu (More) button
3. Select "WikiLens"
4. Grant photo access permission when prompted
5. Select a photo from the grid
6. View identified features
7. Tap any feature to read its Wikipedia article

## Technical Details

### Permissions

- Uses `READ_MEDIA_IMAGES` permission (Android 13+)
- Falls back gracefully if permission is denied
- Clear permission rationale dialogs

### Image Recognition

- Google ML Kit Image Labeling (v17.0.9)
- Confidence threshold: 50%
- Maximum results: 10 per image
- Categories: Landmarks, Animals, Plants, Objects, Persons, Artwork, Other

### State Management

The feature uses a sealed class hierarchy for clean state management:

- `Loading`: Initial state while loading photos
- `PermissionDenied`: Permission not granted
- `PhotosLoaded`: Display photo grid
- `AnalyzingPhoto`: Processing image
- `FeaturesExtracted`: Display results
- `Error`: Handle errors gracefully

## Future Enhancements

Potential improvements:
- Support for custom AI models (e.g., specialized plant/animal recognition)
- Batch processing of multiple photos
- Save recognized features to user's history
- Integration with Reading Lists
- Multi-language support for article suggestions
- Camera integration for real-time recognition
- Improved categorization and filtering
