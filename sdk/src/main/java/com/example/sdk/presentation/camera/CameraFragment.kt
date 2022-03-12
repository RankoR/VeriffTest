package com.example.sdk.presentation.camera

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.ArePermissionsGranted
import com.example.core_ui.presentation.BaseFragment
import com.example.core_ui.util.setOnSingleClickListener
import com.example.sdk.data.model.CameraType
import com.example.sdk.databinding.FragmentCameraBinding
import com.example.sdk.di.DiHolder
import com.example.sdk.di.module.FragmentModule
import com.example.sdk.domain.camera.CameraProviderWrapper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::inflate) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var arePermissionsGranted: ArePermissionsGranted

    @Inject
    protected lateinit var cameraProviderWrapper: CameraProviderWrapper

    override val viewModel: CameraViewModel by viewModels { viewModelFactory }

    private lateinit var cameraType: CameraType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializedDi()
    }

    private fun initializedDi() {
        DiHolder
            .sdkComponent
            .fragmentComponentBuilder
            .fragmentModule(FragmentModule(requireContext()))
            .build()
            .inject(this)
    }

    override fun setupView() {
        super.setupView()

        cameraType = arguments
            ?.getInt(ARG_CAMERA_TYPE)
            ?.let { CameraType.values()[it] }
            ?: CameraType.MAIN

        checkPermissionsAndStart()

        binding?.takePhotoBtn?.setOnSingleClickListener {
            takePhoto()
        }
    }

    private fun checkPermissionsAndStart() {
        if (arePermissionsGranted.exec(Manifest.permission.CAMERA)) {
            startCamera()
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun startCamera() {
        binding
            ?.previewView
            ?.surfaceProvider
            ?.let { surfaceProvider ->
                cameraProviderWrapper.start(cameraType, surfaceProvider, this)
            }
            ?: run {
                // TODO: Error
                return
            }

        lifecycleScope.launch {
            cameraProviderWrapper
                .imageFlow
                .catch { t ->
                    Timber.e(t, "Failed to get a photo")
                    // TODO: Error
                }
                .collect { imageProxy ->
                    Timber.d("Got an image proxy: $imageProxy")
                }
        }
    }

    private fun takePhoto() {
        cameraProviderWrapper.takePicture()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        Timber.d("Permission granted: $isGranted")

        if (isGranted) {
            checkPermissionsAndStart()
        } else {
            // TODO: Show dialog
        }
    }

    override fun onDestroy() {
        cameraProviderWrapper.release()

        super.onDestroy()
    }

    companion object {

        private const val ARG_CAMERA_TYPE = "camera_type"

        fun newInstance(cameraType: CameraType): CameraFragment {
            return CameraFragment().apply {
                arguments = bundleOf(ARG_CAMERA_TYPE to cameraType.ordinal)
            }
        }
    }
}
