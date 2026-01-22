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
import org.wikipedia.history.HistoryEntry
import org.wikipedia.page.PageActivity

class WikiLensActivity : ComponentActivity() {

    private val viewModel: WikiLensViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.onCameraPermissionGranted()
        } else {
            showPermissionDeniedDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseTheme {
                val state by viewModel.state.collectAsState()
                WikiLensCameraScreen(
                    state = state,
                    onFeatureClick = { feature ->
                        openWikipediaArticle(feature)
                    },
                    onStartAnalysis = { bitmap ->
                        viewModel.analyzeFrame(bitmap)
                    },
                    onRetry = {
                        checkCameraPermission()
                    },
                    onBack = {
                        finish()
                    }
                )
            }
        }

        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.onCameraPermissionGranted()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.wikilens_permission_title)
            .setMessage(R.string.wikilens_camera_permission_rationale)
            .setPositiveButton(R.string.permission_grant) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton(R.string.permission_deny) { _, _ ->
                finish()
            }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.wikilens_permission_title)
            .setMessage(R.string.wikilens_camera_permission_denied)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
            }
            .show()
    }

    private fun openWikipediaArticle(feature: ExtractedFeature) {
        val title = feature.toPageTitle()
        val entry = HistoryEntry(title, HistoryEntry.SOURCE_WIKILENS)
        val intent = PageActivity.newIntentForNewTab(this, entry, title)
        startActivity(intent)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, WikiLensActivity::class.java)
        }
    }
}
