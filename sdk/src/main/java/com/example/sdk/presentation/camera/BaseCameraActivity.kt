package com.example.sdk.presentation.camera

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.example.core_ui.presentation.BaseActivity
import com.example.core_ui.util.ActivityInflate
import com.example.sdk.data.model.CameraType
import com.example.sdk.data.model.PhotoResult
import com.example.sdk.presentation.loading.LoadingFragment
import timber.log.Timber
import java.io.File

abstract class BaseCameraActivity<VB : ViewBinding>(
    inflate: ActivityInflate<VB>,
    private val cameraType: CameraType
) : BaseActivity<VB>(inflate) {

    protected abstract val fragmentContainerId: Int

    abstract fun onGotPhoto(file: File)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.setFragmentResultListener(
            CameraFragment.PHOTO_REQUEST_KEY,
            this
        ) { requestKey, result ->
            Timber.d("Got a fragment result: key=$requestKey, result=$result")

            result
                .getParcelable<PhotoResult>(CameraFragment.PHOTO_RESULT_KEY)
                ?.let { photoResult ->
                    when (photoResult) {
                        is PhotoResult.Success -> onGotPhoto(photoResult.filePath.let(::File))
                        is PhotoResult.Failure -> {
                            // TODO: Show error
                        }
                    }
                }
        }
    }

    protected fun startCameraFlow() {
        supportFragmentManager.commit {
            replace(fragmentContainerId, CameraFragment.newInstance(cameraType))
        }
    }

    protected fun showLoading() {
        supportFragmentManager.commit {
            replace(fragmentContainerId, LoadingFragment.newInstance())
        }
    }
}
