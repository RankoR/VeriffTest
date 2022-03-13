package com.example.sdk

import com.example.core_tests.BaseInstrumentedTest
import com.example.sdk.domain.face.interactor.DetectFace
import com.example.sdk.domain.face.interactor.DetectFaceImpl
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertTrue
import org.junit.Test

class DetectFaceTest : BaseInstrumentedTest() {

    private val detectFace: DetectFace = DetectFaceImpl(
        targetContext,
        Dispatchers.Default
    )

    @Test
    fun detectNormalMaleFace() {
        assertTrue(false)
    }

    @Test
    fun detectNormalFemaleFace() {
        assertTrue(false)
    }

    @Test
    fun detectEmptyImage() {
        assertTrue(false)
    }

    @Test
    fun detectNoFaces() {
        assertTrue(false)
    }

    @Test
    fun detectEyesClosed() {
        assertTrue(false)
    }

    @Test
    fun detectHeadRotated() {
        assertTrue(false)
    }

    @Test
    fun detectMultipleFaces() {
        assertTrue(false)
    }
}
