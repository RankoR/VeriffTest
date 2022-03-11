package com.example.sdk.presentation.camera

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.core.domain.ArePermissionsGranted
import com.example.core_ui.presentation.BaseFragment
import com.example.core_ui.util.setOnSingleClickListener
import com.example.sdk.data.model.CameraType
import com.example.sdk.databinding.FragmentCameraBinding
import com.example.sdk.di.DiHolder
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Inject

class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::inflate) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var arePermissionsGranted: ArePermissionsGranted

    override val viewModel: CameraViewModel by viewModels { viewModelFactory }

    private lateinit var cameraType: CameraType

    private var imageCapture: ImageCapture? = null
    private val cameraExecutor by lazy { Executors.newSingleThreadExecutor() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializedDi()
    }

    private fun initializedDi() {
        DiHolder.sdkComponent.inject(this)
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

    override fun onDestroy() {
        cameraExecutor.shutdown()

        super.onDestroy()
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
        val context = context ?: return

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .apply {
                    binding?.previewView?.surfaceProvider?.let(::setSurfaceProvider)
                }

            val cameraSelector = when (cameraType) {
                CameraType.MAIN -> CameraSelector.DEFAULT_BACK_CAMERA
                CameraType.FRONT -> CameraSelector.DEFAULT_FRONT_CAMERA
            }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.apply {
                    unbindAll()
                    bindToLifecycle(this@CameraFragment, cameraSelector, preview, imageCapture)
                }
            } catch (e: Exception) {
                Timber.e(e, "Use case binding failed")

                // TODO: Show error
            }
        }, ContextCompat.getMainExecutor(context))
        }

        private fun takePhoto() {
            imageCapture?.takePicture(
                cameraExecutor,
                object : OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        super.onCaptureSuccess(image)

                        Timber.d("onCaptureSuccess")
                    }

                    override fun onError(exception: ImageCaptureException) {
                        super.onError(exception)

                        Timber.e(exception, "Failed to take a photo")

                        // TODO: Show error
                    }
                }
            )
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

        companion object {

            private const val ARG_CAMERA_TYPE = "camera_type"

            fun newInstance(cameraType: CameraType): CameraFragment {
                return CameraFragment().apply {
                    arguments = bundleOf(ARG_CAMERA_TYPE to cameraType.ordinal)
                }
            }
        }
    }
