@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.sdk.domain

import com.example.core_tests.BaseInstrumentedTest
import com.example.core_tests.assertThrows
import com.example.core_tests.awaitSingle
import com.example.sdk.data.model.face.FaceData
import com.example.sdk.domain.face.exception.EyesClosedException
import com.example.sdk.domain.face.exception.HeadRotationException
import com.example.sdk.domain.face.exception.MissingFaceException
import com.example.sdk.domain.face.exception.MultipleFacesException
import com.example.sdk.domain.face.interactor.DetectFace
import com.example.sdk.domain.face.interactor.DetectFaceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertNotNull

class DetectFaceTest : BaseInstrumentedTest() {

    private val detectFace: DetectFace = DetectFaceImpl(
        targetContext,
        Dispatchers.Default
    )

    @Test
    fun detectNormalMaleFace() {
        val file = getFileFromAssets("images/faces/normal-male.jpg")

        runTest {
            detectFace
                .exec(file)
                .awaitSingle { data ->
                    assertTrue(
                        "Eyes open probability: left=${data.leftEyeOpenProbability}, right=${data.rightEyeOpenProbability}",
                        data.areBothEyesOpen
                    )
                    assertTrue("Smiling probability = ${data.smilingProbability}", data.smilingProbability > .5f)

                    assertNotNull(data.contours.find { it.type == FaceData.FaceContour.Type.FACE })
                }
        }
    }

    @Test
    fun detectNormalFemaleFace() {
        val file = getFileFromAssets("images/faces/normal-female.jpg")

        runTest {
            detectFace
                .exec(file)
                .awaitSingle { data ->
                    assertTrue(
                        "Eyes open probability: left=${data.leftEyeOpenProbability}, right=${data.rightEyeOpenProbability}",
                        data.areBothEyesOpen
                    )
                    assertTrue("Smiling probability = ${data.smilingProbability}", data.smilingProbability < .5f)

                    assertNotNull(data.contours.find { it.type == FaceData.FaceContour.Type.FACE })
                }
        }
    }

    @Test
    fun detectEmptyImage() {
        val file = getFileFromAssets("images/faces/empty.jpeg")

        runTest {
            detectFace
                .exec(file)
                .assertThrows<MissingFaceException>()
        }
    }

    @Test
    fun detectNoFaces() {
        val file = getFileFromAssets("images/faces/no-face.jpg")

        runTest {
            detectFace
                .exec(file)
                .assertThrows<MissingFaceException>()
        }
    }

    @Test
    fun detectEyesClosed() {
        val file = getFileFromAssets("images/faces/closed-eyes.jpeg")

        runTest {
            detectFace
                .exec(file)
                .assertThrows<EyesClosedException>()
        }
    }

    @Test
    fun detectHeadRotated() {
        val file = getFileFromAssets("images/faces/rotated.jpeg")

        runTest {
            detectFace
                .exec(file)
                .assertThrows<HeadRotationException>()
        }
    }

    @Test
    fun detectMultipleFaces() {
        val file = getFileFromAssets("images/faces/multiple-faces.jpg")

        runTest {
            detectFace
                .exec(file)
                .assertThrows<MultipleFacesException>()
        }
    }
}
