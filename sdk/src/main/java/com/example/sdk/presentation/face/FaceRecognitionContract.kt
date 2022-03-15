package com.example.sdk.presentation.face

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.sdk.VeriffSdk
import com.example.sdk.data.model.face.FaceResult

/**
 * Used to launch [FaceRecognitionActivity] and return the result of its work
 */
internal class FaceRecognitionContract : ActivityResultContract<Unit, FaceResult>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return VeriffSdk.createFaceRecognitionIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): FaceResult {
        return when (resultCode) {
            Activity.RESULT_CANCELED -> FaceResult.Cancelled
            else -> intent.parseResult()
        }
    }

    private fun Intent?.parseResult(): FaceResult {
        return this
            ?.getParcelableExtra(KEY_FACE_RESULT)
            ?: FaceResult.Failure(text = "No result")
    }

    companion object {
        const val KEY_FACE_RESULT = "face_result"

        const val RESULT_FAILURE = -2
    }
}
