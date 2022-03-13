package com.example.sdk.presentation.face

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sdk.R
import com.example.sdk.data.model.CameraType
import com.example.sdk.databinding.ActivityFaceRecognitionBinding
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.camera.BaseCameraActivity
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class FaceRecognitionActivity : BaseCameraActivity<ActivityFaceRecognitionBinding>(
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
        Timber.d("onGotPhoto: ${file.absolutePath}")

        showLoading()

        viewModel.onGotPhoto(file)
    }
}
