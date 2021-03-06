package com.example.sdk.domain.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.Preview.SurfaceProvider
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.sdk.data.model.CameraType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executors

/**
 * Wrapper for the CameraX provider
 *
 * @property imageFlow Emits the taken photos
 */
internal interface CameraProviderWrapper {
    val imageFlow: Flow<File>

    /**
     * Start the camera provider
     *
     * @param cameraType Type of the camera to use
     * @param surfaceProvider Surface for preview
     * @param lifecycleOwner Lifecycle owner (for example, view lifecycle owner for fragment)
     *
     * @see CameraType
     * @see SurfaceProvider
     * @see LifecycleOwner
     */
    fun start(cameraType: CameraType, surfaceProvider: SurfaceProvider, lifecycleOwner: LifecycleOwner)

    /**
     * Take picture
     *
     * If something is wrong, fails silently
     *
     * TODO: Add some error indicator ([Flow] with errors, etc)
     */
    fun takePicture()

    /**
     * Release the provider. Call in, for example, Fragment's onDestroy
     */
    fun release()
}

/**
 * Note: Here are two memory leaks (when you rotate the device), but it seems that it's an in-library leak.
 */
internal class CameraProviderWrapperImpl(
    private val context: Context
) : CameraProviderWrapper {

    override val imageFlow: MutableSharedFlow<File> = MutableSharedFlow(replay = 1)

    private var imageCapture: ImageCapture? = null
    private val cameraExecutor by lazy { Executors.newSingleThreadExecutor() }

    override fun start(cameraType: CameraType, surfaceProvider: SurfaceProvider, lifecycleOwner: LifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(surfaceProvider)
                }

            val cameraSelector = when (cameraType) {
                CameraType.MAIN -> CameraSelector.DEFAULT_BACK_CAMERA
                CameraType.FRONT -> CameraSelector.DEFAULT_FRONT_CAMERA
            }

            imageCapture = ImageCapture
                .Builder()
                .build()

            try {
                cameraProvider.apply {
                    unbindAll()
                    bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
                }
            } catch (e: Exception) {
                Timber.e(e, "Use case binding failed")

                // TODO: Show error
            }
        }, ContextCompat.getMainExecutor(context))
        }

        override fun takePicture() {
            val outputFile = getOutputFile()

            val outputFileOptions = ImageCapture
                .OutputFileOptions
                .Builder(outputFile)
                .build()

            imageCapture?.takePicture(
                outputFileOptions,
                cameraExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Timber.d("onImageSaved: ${outputFileResults.savedUri}")

                        val isEmitted = imageFlow.tryEmit(outputFile)
                        Timber.d("Is emitted: $isEmitted")
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Timber.e(exception, "Failed to take a photo")

                        // TODO: Show error
                    }
                }
            )
        }

        override fun release() {
            ProcessCameraProvider.getInstance(context).get()?.unbindAll()
            cameraExecutor.shutdown()
            imageCapture = null
        }

        private fun getOutputFile(): File {
            // I don't think there could be collisions
            return File(context.cacheDir, System.currentTimeMillis().toString())
        }
    }
