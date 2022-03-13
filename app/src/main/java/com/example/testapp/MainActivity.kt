package com.example.testapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import androidx.exifinterface.media.ExifInterface
import com.example.core_ui.presentation.BaseActivity
import com.example.core_ui.util.setOnSingleClickListener
import com.example.sdk.VeriffSdk
import com.example.sdk.data.model.face.FaceResult
import com.example.sdk.data.model.id.TextDocumentResult
import com.example.testapp.databinding.ActivityMainBinding
import timber.log.Timber
import java.io.File

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeVeriffSdk()

        binding.readTextBtn.setOnSingleClickListener {
            VeriffSdk.launchIdRecognition()
        }

        binding.detectFaceBtn.setOnSingleClickListener {
            VeriffSdk.launchFaceRecognition()
        }
    }

    private fun initializeVeriffSdk() {
        VeriffSdk.registerActivity(this)

        VeriffSdk.onTextDocumentResult = { result ->
            Timber.d("Text document result: $result")

            result.handleResult()
        }

        VeriffSdk.onFaceResult = { result ->
            Timber.d("Face result: $result")

            result.handleResult()
        }
    }

    private fun TextDocumentResult.handleResult() {
        when (this) {
            TextDocumentResult.Cancelled -> showError(getString(R.string.title_message_cancelled)) { VeriffSdk.launchIdRecognition() }
            is TextDocumentResult.Failure -> showError(text) { VeriffSdk.launchIdRecognition() }
            is TextDocumentResult.Success -> {
                binding.textTv.text = documentData.toString()
                // TODO: RecyclerView
            }
        }
    }

    private fun FaceResult.handleResult() {
        when (this) {
            FaceResult.Cancelled -> showError(getString(R.string.title_message_cancelled)) { VeriffSdk.launchIdRecognition() }
            is FaceResult.Failure -> showError(text) { VeriffSdk.launchIdRecognition() }
            is FaceResult.Success -> {
                photoFile
                    .loadBitmap()
                    .let(binding.faceIv::setImageBitmap)
            }
        }
    }

    /**
     * Loads bitmap fixing its rotation
     *
     * This is not really safe as on the low-end pre-7 devices it can cause OOM
     * But it's a sample app, so it's OK
     */
    private fun File.loadBitmap(): Bitmap {
        val rotation = ExifInterface(this).rotationDegrees
        val bitmap = BitmapFactory.decodeFile(absolutePath)
        val matrix = Matrix().apply { postRotate(rotation.toFloat()) }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun showError(text: String, onRetryClick: () -> Unit) {
        // TODO
    }
}
