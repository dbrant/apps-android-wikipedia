package org.wikipedia.wikilens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.wikipedia.R
import org.wikipedia.compose.theme.BaseTheme
import org.wikipedia.page.PageActivity

class WikiLensActivity : ComponentActivity() {

    private val viewModel: WikiLensViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.loadRecentPhotos(this)
        } else {
            showPermissionDeniedDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseTheme {
                val state by viewModel.state.collectAsState()
                WikiLensScreen(
                    state = state,
                    onPhotoClick = { photo ->
                        viewModel.analyzePhoto(photo)
                    },
                    onFeatureClick = { feature ->
                        openWikipediaArticle(feature)
                    },
                    onRetry = {
                        checkPermissionAndLoadPhotos()
                    },
                    onBack = {
                        finish()
                    }
                )
            }
        }

        checkPermissionAndLoadPhotos()
    }

    private fun checkPermissionAndLoadPhotos() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.loadRecentPhotos(this)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.wikilens_permission_title)
            .setMessage(R.string.wikilens_permission_rationale)
            .setPositiveButton(R.string.permission_grant) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
            .setNegativeButton(R.string.permission_deny) { _, _ ->
                finish()
            }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.wikilens_permission_title)
            .setMessage(R.string.wikilens_permission_denied)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
            }
            .show()
    }

    private fun openWikipediaArticle(feature: ExtractedFeature) {
        val title = feature.toPageTitle()
        val entry = org.wikipedia.history.HistoryEntry(title, org.wikipedia.history.HistoryEntry.SOURCE_WIKILENS)
        val intent = PageActivity.newIntentForNewTab(
            this,
            entry,
            title
        )
        startActivity(intent)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, WikiLensActivity::class.java)
        }
    }
}
