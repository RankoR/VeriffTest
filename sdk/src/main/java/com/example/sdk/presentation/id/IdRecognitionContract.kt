package com.example.sdk.presentation.id

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.sdk.VeriffSdk
import com.example.sdk.data.model.id.TextDocumentResult

/**
 * Used to launch [IdRecognitionActivity] and return the result of its work
 */
internal class IdRecognitionContract : ActivityResultContract<Unit, TextDocumentResult>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return VeriffSdk.createIdRecognitionIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): TextDocumentResult {
        return when (resultCode) {
            Activity.RESULT_CANCELED -> TextDocumentResult.Cancelled
            else -> intent.parseResult()
        }
    }

    private fun Intent?.parseResult(): TextDocumentResult {
        return this
            ?.getParcelableExtra(KEY_DOCUMENT_RESULT)
            ?: TextDocumentResult.Failure(text = "No result")
    }

    companion object {
        const val KEY_DOCUMENT_RESULT = "document_result"

        const val RESULT_FAILURE = -2
    }
}
