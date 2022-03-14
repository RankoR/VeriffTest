@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.sdk.viewmodel

import android.graphics.Rect
import com.example.core_tests.BaseInstrumentedTest
import com.example.core_tests.awaitSingle
import com.example.sdk.data.model.face.FaceData
import com.example.sdk.data.model.face.FaceResult
import com.example.sdk.domain.face.exception.MissingFaceException
import com.example.sdk.domain.face.interactor.DetectFace
import com.example.sdk.presentation.face.FaceRecognitionViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FaceRecognitionViewModelTest : BaseInstrumentedTest() {

    private val detectFace: DetectFace = mockk()
    private val viewModel = FaceRecognitionViewModel(
        detectFace = detectFace
    )

    @Test
    fun normalDetection() {
        val faceData = FaceData(
            contours = emptyList(),
            landmarks = emptyList(),
            boundingBox = Rect(),
            headEulerAngleX = 0f,
            headEulerAngleY = 0f,
            headEulerAngleZ = 0f,
            leftEyeOpenProbability = 1f,
            rightEyeOpenProbability = 1f,
            smilingProbability = 1f
        )

        coEvery { detectFace.exec(any()) } returns flowOf(faceData)

        viewModel.onGotPhoto(File("/"))

        runTest {
            viewModel
                .faceResult
                .awaitSingle { result ->
                    assertIs<FaceResult.Success>(result)

                    assertEquals(faceData, result.faceData)
                }
        }
    }

    @Test
    fun errorInDetection() {
        coEvery { detectFace.exec(any()) } returns flow { throw MissingFaceException() }

        viewModel.onGotPhoto(File("/"))

        runTest {
            viewModel
                .faceResult
                .awaitSingle { result ->
                    assertIs<FaceResult.Failure>(result)

                    assertEquals("No face found", result.text)
                }
        }
    }
}
