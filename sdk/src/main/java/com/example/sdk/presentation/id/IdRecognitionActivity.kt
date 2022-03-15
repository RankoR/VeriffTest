package com.example.sdk.presentation.id

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sdk.R
import com.example.sdk.data.model.CameraType
import com.example.sdk.data.model.id.TextDocumentResult
import com.example.sdk.databinding.ActivityIdRecognitionBinding
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.camera.BaseCameraActivity
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

internal class IdRecognitionActivity : BaseCameraActivity<ActivityIdRecognitionBinding>(
    ActivityIdRecognitionBinding::inflate,
    CameraType.MAIN
) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override val fragmentContainerId: Int = R.id.fragmentContainer

    override val viewModel: IdRecognitionViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        initializedDi()

        super.onCreate(savedInstanceState)

        startCameraFlow()
    }

    private fun initializedDi() {
        DiHolder.sdkComponent.inject(this)
    }

    override fun onGotPhoto(file: File) {
        showLoading()

        viewModel.onGotPhoto(file)
    }

    override fun setupViewModel() {
        super.setupViewModel()

        lifecycleScope.launch {
            viewModel.documentResult.collect(::returnResult)
        }
    }

    private fun returnResult(result: TextDocumentResult) {
        Intent()
            .apply {
                putExtra(IdRecognitionContract.KEY_DOCUMENT_RESULT, result)
            }
            .let { intent ->
                val resultCode = when (result) {
                    is TextDocumentResult.Success -> RESULT_OK
                    is TextDocumentResult.Cancelled -> RESULT_CANCELED
                    is TextDocumentResult.Failure -> IdRecognitionContract.RESULT_FAILURE
                }

                Timber.d("Setting result: $result")
                setResult(resultCode, intent)
                finish()
            }
    }
}
