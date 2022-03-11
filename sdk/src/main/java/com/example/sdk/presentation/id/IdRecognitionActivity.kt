package com.example.sdk.presentation.id

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sdk.R
import com.example.sdk.data.model.CameraType
import com.example.sdk.databinding.ActivityIdRecognitionBinding
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.camera.BaseCameraActivity
import timber.log.Timber
import javax.inject.Inject

class IdRecognitionActivity : BaseCameraActivity<ActivityIdRecognitionBinding>(
    ActivityIdRecognitionBinding::inflate,
    CameraType.MAIN
) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override val fragmentContainerId: Int = R.id.fragmentContainer

    override val viewModel: IdRecognitionViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializedDi()

        startCameraFlow()
    }

    private fun initializedDi() {
        DiHolder.sdkComponent.inject(this)
    }

    override fun onGotPhoto(bitmap: Bitmap) {
        Timber.d("Got a photo: $bitmap")
    }
}
