package org.wikipedia.suggestededits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import org.wikipedia.Constants.InvokeSource
import org.wikipedia.Constants.InvokeSource.*
import org.wikipedia.R
import org.wikipedia.activity.SingleFragmentActivity
import org.wikipedia.analytics.SuggestedEditsFunnel
import org.wikipedia.suggestededits.SuggestedEditsCardsFragment.Companion.newInstance
import org.wikipedia.util.FeedbackUtil
import org.wikipedia.util.ResourceUtil
import org.wikipedia.views.DialogTitleWithImage

class SuggestedEditsCardsActivity : SingleFragmentActivity<SuggestedEditsCardsFragment>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(getActionBarTitleRes(intent.getSerializableExtra(EXTRA_SOURCE) as InvokeSource))
        setStatusBarColor(ResourceUtil.getThemedAttributeId(this, R.attr.suggestions_background_color))
    }

    override fun createFragment(): SuggestedEditsCardsFragment {
        return newInstance(intent.getSerializableExtra(EXTRA_SOURCE) as InvokeSource)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_suggested_edits, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_help -> {
                FeedbackUtil.showAndroidAppEditingFAQ(baseContext)
                true
            }
            R.id.menu_my_contributions -> {
                startActivity(SuggestedEditsContributionsActivity.newIntent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getActionBarTitleRes(invokeSource: InvokeSource): Int {
        return when(invokeSource) {
            SUGGESTED_EDITS_TRANSLATE_DESC -> {
                R.string.suggested_edits_translate_descriptions
            }
            SUGGESTED_EDITS_ADD_CAPTION -> {
                R.string.suggested_edits_add_image_captions
            }
            SUGGESTED_EDITS_TRANSLATE_CAPTION -> {
                R.string.suggested_edits_translate_image_captions
            }
            else -> R.string.suggested_edits_add_descriptions
        }
    }

    companion object {
        const val EXTRA_SOURCE = "source"
        const val EXTRA_SOURCE_ADDED_CONTRIBUTION = "addedContribution"

        fun newIntent(context: Context, source: InvokeSource): Intent {
            return Intent(context, SuggestedEditsCardsActivity::class.java).putExtra(EXTRA_SOURCE, source)
        }

        fun showEditDescriptionUnlockDialog(context: Context) {
            AlertDialog.Builder(context)
                    .setCustomTitle(DialogTitleWithImage(context, R.string.suggested_edits_unlock_add_descriptions_dialog_title, R.drawable.ic_unlock_illustration_add, true))
                    .setMessage(R.string.suggested_edits_unlock_add_descriptions_dialog_message)
                    .setPositiveButton(R.string.suggested_edits_unlock_dialog_yes) { _, _ ->
                        SuggestedEditsFunnel.get(ONBOARDING_DIALOG)
                        context.startActivity(SuggestedEditsTasksActivity.newIntent(context, SUGGESTED_EDITS_ADD_DESC))
                    }
                    .setNegativeButton(R.string.suggested_edits_unlock_dialog_no, null)
                    .show()
        }

        fun showTranslateDescriptionUnlockDialog(context: Context) {
            AlertDialog.Builder(context)
                    .setCustomTitle(DialogTitleWithImage(context, R.string.suggested_edits_unlock_translate_descriptions_dialog_title, R.drawable.ic_unlock_illustration_translate, true))
                    .setMessage(R.string.suggested_edits_unlock_translate_descriptions_dialog_message)
                    .setPositiveButton(R.string.suggested_edits_unlock_dialog_yes) { _, _ ->
                        SuggestedEditsFunnel.get(ONBOARDING_DIALOG)
                        context.startActivity(SuggestedEditsTasksActivity.newIntent(context, SUGGESTED_EDITS_TRANSLATE_DESC))
                    }
                    .setNegativeButton(R.string.suggested_edits_unlock_dialog_no, null)
                    .show()
        }

        fun showEditCaptionUnlockDialog(context: Context) {
            AlertDialog.Builder(context)
                    .setCustomTitle(DialogTitleWithImage(context, R.string.suggested_edits_unlock_add_captions_dialog_title, R.drawable.ic_unlock_illustration_add_captions, true))
                    .setMessage(R.string.suggested_edits_unlock_add_captions_dialog_message)
                    .setPositiveButton(R.string.suggested_edits_unlock_dialog_yes) { _, _ ->
                        SuggestedEditsFunnel.get(ONBOARDING_DIALOG)
                        context.startActivity(SuggestedEditsTasksActivity.newIntent(context, SUGGESTED_EDITS_ADD_CAPTION))
                    }
                    .setNegativeButton(R.string.suggested_edits_unlock_dialog_no, null)
                    .show()
        }

        fun showTranslateCaptionUnlockDialog(context: Context) {
            AlertDialog.Builder(context)
                    .setCustomTitle(DialogTitleWithImage(context, R.string.suggested_edits_unlock_translate_captions_dialog_title, R.drawable.ic_unlock_illustration_translate_captions, true))
                    .setMessage(R.string.suggested_edits_unlock_translate_captions_dialog_message)
                    .setPositiveButton(R.string.suggested_edits_unlock_dialog_yes) { _, _ ->
                        SuggestedEditsFunnel.get(ONBOARDING_DIALOG)
                        context.startActivity(SuggestedEditsTasksActivity.newIntent(context, SUGGESTED_EDITS_TRANSLATE_CAPTION))
                    }
                    .setNegativeButton(R.string.suggested_edits_unlock_dialog_no, null)
                    .show()
        }
    }
}
