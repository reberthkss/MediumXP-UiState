package br.com.dialogystudios.tododetail.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class UpdateTodoContract: ActivityResultContract<String, Boolean>() {
    override fun createIntent(context: Context, todoID: String?): Intent =
        Intent(Intent.ACTION_VIEW)
            .apply {
                data = Uri.parse("mediumxp://todo-update?id=${todoID}")
            }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if (resultCode != Activity.RESULT_OK) {
            return false
        }
        return intent?.getBooleanExtra(BUNDLE_WAS_UPDATED, false) ?: false
    }

    companion object {
        const val BUNDLE_WAS_UPDATED = "was_updated"
    }
}