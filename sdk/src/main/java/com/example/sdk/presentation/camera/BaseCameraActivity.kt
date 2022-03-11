package com.example.sdk.presentation.camera

import android.graphics.Bitmap
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.example.core_ui.presentation.BaseActivity
import com.example.core_ui.util.ActivityInflate
import com.example.sdk.data.model.CameraType

abstract class BaseCameraActivity<VB : ViewBinding>(
    inflate: ActivityInflate<VB>,
    private val cameraType: CameraType
) : BaseActivity<VB>(inflate) {

    protected abstract val fragmentContainerId: Int

    protected fun startCameraFlow() {
        supportFragmentManager.commit {
            replace(fragmentContainerId, CameraFragment.newInstance(cameraType))
        }
    }

    abstract fun onGotPhoto(bitmap: Bitmap)
}
