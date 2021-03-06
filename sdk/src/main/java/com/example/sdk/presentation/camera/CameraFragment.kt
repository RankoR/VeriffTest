package com.example.sdk.presentation.camera

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.ArePermissionsGranted
import com.example.core_ui.presentation.BaseFragment
import com.example.core_ui.presentation.BaseViewModel
import com.example.core_ui.util.setOnSingleClickListener
import com.example.sdk.data.model.CameraType
import com.example.sdk.data.model.PhotoResult
import com.example.sdk.databinding.FragmentCameraBinding
import com.example.sdk.di.DiHolder
import com.example.sdk.di.module.FragmentModule
import com.example.sdk.domain.camera.CameraProviderWrapper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Fragment with a camera preview
 */
internal class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::inflate) {

    @Inject
    protected lateinit var arePermissionsGranted: ArePermissionsGranted

    @Inject
    protected lateinit var cameraProviderWrapper: CameraProviderWrapper

    /**
     * Camera to use
     */
    private lateinit var cameraType: CameraType

    // TODO: ViewModel is not yet used in this fragment
    override val viewModel: BaseViewModel? = null

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

    /**
     * Check if camera permission is granted and start the camera if so
     */
    private fun checkPermissionsAndStart() {
        if (arePermissionsGranted.exec(Manifest.permission.CAMERA)) {
            startCamera()
        } else {
            requestPermissions()
        }
    }

    /**
     * Request the camera permission
     */
    private fun requestPermissions() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    /**
     * Initialize the camera and start the preview
     */
    private fun startCamera() {
        binding
            ?.previewView
            ?.surfaceProvider
            ?.let { surfaceProvider ->
                cameraProviderWrapper.start(cameraType, surfaceProvider, this)
            }
            ?: run {
                setResult(
                    PhotoResult.Failure(
                        errorMessage = "No surface provider"
                    )
                )
                return
            }

        lifecycleScope.launch {
            cameraProviderWrapper
                .imageFlow
                .catch { t ->
                    Timber.e(t, "Failed to get a photo")

                    setResult(
                        PhotoResult.Failure(
                            // Probably there's a better way as in this case we lose a stacktrace
                            errorMessage = t.message.orEmpty()
                        )
                    )
                }
                .collect { file ->
                    Timber.d("Got an image file: ${file.absolutePath}")

                    setResult(
                        PhotoResult.Success(
                            filePath = file.absolutePath
                        )
                    )
                }
        }
    }

    /**
     * Take the photo
     */
    private fun takePhoto() {
        cameraProviderWrapper.takePicture()
    }

    private fun setResult(result: PhotoResult) {
        setFragmentResult(
            PHOTO_REQUEST_KEY,
            bundleOf(
                PHOTO_RESULT_KEY to result
            )
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        Timber.d("Permission granted: $isGranted")

        if (isGranted) {
            checkPermissionsAndStart()
        } else {
            // TODO: Add permission denied flow
            // I didn't add it as:
            // 1. It's an UX decision (we have multiple options: show rationale, send user to settings, etc)
            // 2. I just don't have much time for it
        }
    }

    override fun onDestroy() {
        cameraProviderWrapper.release()

        super.onDestroy()
    }

    companion object {

        private const val ARG_CAMERA_TYPE = "camera_type"

        const val PHOTO_REQUEST_KEY = "photo_request"
        const val PHOTO_RESULT_KEY = "photo_result"

        /**
         * Create the camera fragment
         *
         * @param cameraType Camera to use
         */
        fun newInstance(cameraType: CameraType): CameraFragment {
            return CameraFragment().apply {
                arguments = bundleOf(ARG_CAMERA_TYPE to cameraType.ordinal)
            }
        }
    }
}
