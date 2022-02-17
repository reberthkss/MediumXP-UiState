package br.com.dialogystudios.tododetail.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class DeleteTodoContract: ActivityResultContract<String, Boolean>() {
    override fun createIntent(context: Context, input: String?): Intent = Intent(Intent.ACTION_VIEW)
        .apply {
//            putExtra(BUNDLE_TODO_ID, input)
            data = Uri.parse("mediumxp://todo-delete?id=${input}")
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if (resultCode != Activity.RESULT_OK) {
            return false
        }
        return intent?.getBooleanExtra(BUNDLE_TODO_WAS_REMOVED, false) ?: false
    }

    companion object {
        const val BUNDLE_TODO_WAS_REMOVED = "was_removed"
    }
}