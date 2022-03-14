@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.sdk.domain

import androidx.camera.core.Preview.SurfaceProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.core_tests.BaseInstrumentedTest
import com.example.sdk.data.model.CameraType
import com.example.sdk.domain.camera.CameraProviderWrapper
import com.example.sdk.domain.camera.CameraProviderWrapperImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

/**
 * FIXME: This test doesn't work, as it's a difficult task to mock everything required for the camera functionality
 */
class CameraProviderWrapperTest : BaseInstrumentedTest() {

    private val cameraProviderWrapper: CameraProviderWrapper = CameraProviderWrapperImpl(targetContext)

    private val surfaceProvider = mockk<SurfaceProvider>()

    private val viewLifecycleOwner = mockk<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(viewLifecycleOwner)

    @Before
    fun setupMocks() {
        every { surfaceProvider.onSurfaceRequested(any()) } returns Unit

        runOnUiThread {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        every { viewLifecycleOwner.lifecycle } returns lifecycle
    }

    @Test
    fun takePhoto() {
        cameraProviderWrapper.start(
            cameraType = CameraType.MAIN,
            surfaceProvider = surfaceProvider,
            lifecycleOwner = viewLifecycleOwner
        )
    }
}
