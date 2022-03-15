package com.example.sdk.presentation.face

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sdk.R
import com.example.sdk.data.model.CameraType
import com.example.sdk.data.model.face.FaceResult
import com.example.sdk.databinding.ActivityFaceRecognitionBinding
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.camera.BaseCameraActivity
import com.example.sdk.presentation.id.IdRecognitionContract
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * Used for face recognition
 */
internal class FaceRecognitionActivity : BaseCameraActivity<ActivityFaceRecognitionBinding>(
    ActivityFaceRecognitionBinding::inflate,
    CameraType.FRONT
) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override val fragmentContainerId: Int = R.id.fragmentContainer

    override val viewModel: FaceRecognitionViewModel by viewModels { viewModelFactory }

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
            viewModel.faceResult.collect(::returnResult)
        }
    }

    private fun returnResult(result: FaceResult) {
        Intent()
            .apply {
                putExtra(FaceRecognitionContract.KEY_FACE_RESULT, result)
            }
            .let { intent ->
                val resultCode = when (result) {
                    is FaceResult.Success -> RESULT_OK
                    is FaceResult.Cancelled -> RESULT_CANCELED
                    is FaceResult.Failure -> IdRecognitionContract.RESULT_FAILURE
                }

                Timber.d("Setting result: $result")
                setResult(resultCode, intent)
                finish()
            }
    }
}
