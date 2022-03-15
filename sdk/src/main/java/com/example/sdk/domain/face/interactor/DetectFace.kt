package com.example.sdk.domain.face.interactor

import android.content.Context
import androidx.core.net.toUri
import com.example.sdk.data.model.face.FaceData
import com.example.sdk.domain.face.exception.EyesClosedException
import com.example.sdk.domain.face.exception.HeadRotationException
import com.example.sdk.domain.face.exception.MissingFaceException
import com.example.sdk.domain.face.exception.MultipleFacesException
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.io.File

internal interface DetectFace {
    suspend fun exec(file: File): Flow<FaceData>
}

internal class DetectFaceImpl(
    private val context: Context,
    private val coroutineDispatcher: CoroutineDispatcher
) : DetectFace {

    private val faceDetector by lazy {
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .build()
            .let(FaceDetection::getClient)
    }

    override suspend fun exec(file: File): Flow<FaceData> {
        return callbackFlow {
            val inputImage = InputImage.fromFilePath(context, file.toUri())

            faceDetector
                .process(inputImage)
                .addOnFailureListener { t -> close(t) }
                .addOnSuccessListener { faces ->
                    if (faces.isEmpty()) {
                        close(MissingFaceException())
                    } else if (faces.size > 1) {
                        close(MultipleFacesException())
                    } else {
                        faces
                            .first()
                            .toInternalFaceData()
                            .let { faceData ->
                                if (!faceData.areBothEyesOpen) {
                                    EyesClosedException(
                                        leftOpenProbability = faceData.leftEyeOpenProbability,
                                        rightOpenProbability = faceData.rightEyeOpenProbability
                                    ).let(::close)
                                } else if (!faceData.isHeadRotationValid) {
                                    close(HeadRotationException())
                                } else {
                                    trySend(faceData)
                                }
                            }
                    }
                }

            awaitClose {
                // Unfortunately it's not cancellable
            }
        }.flowOn(coroutineDispatcher)
    }

    private fun Face.toInternalFaceData(): FaceData {
        return FaceData(
            contours = allContours.mapNotNull { it.toInternalModel() },
            landmarks = allLandmarks.mapNotNull { it.toInternalModel() },
            boundingBox = boundingBox,
            headEulerAngleX = headEulerAngleX,
            headEulerAngleY = headEulerAngleY,
            headEulerAngleZ = headEulerAngleZ,
            leftEyeOpenProbability = leftEyeOpenProbability ?: 1f,
            rightEyeOpenProbability = rightEyeOpenProbability ?: 1f,
            smilingProbability = smilingProbability ?: 0f
        )
    }

    private fun FaceContour.toInternalModel(): FaceData.FaceContour? {
        val type = when (faceContourType) {
            FaceContour.FACE -> FaceData.FaceContour.Type.FACE
            FaceContour.LEFT_EYEBROW_TOP -> FaceData.FaceContour.Type.LEFT_EYEBROW_TOP
            FaceContour.LEFT_EYEBROW_BOTTOM -> FaceData.FaceContour.Type.LEFT_EYEBROW_BOTTOM
            FaceContour.RIGHT_EYEBROW_TOP -> FaceData.FaceContour.Type.RIGHT_EYEBROW_TOP
            FaceContour.RIGHT_EYEBROW_BOTTOM -> FaceData.FaceContour.Type.RIGHT_EYEBROW_BOTTOM
            FaceContour.LEFT_EYE -> FaceData.FaceContour.Type.LEFT_EYE
            FaceContour.RIGHT_EYE -> FaceData.FaceContour.Type.RIGHT_EYE
            FaceContour.UPPER_LIP_TOP -> FaceData.FaceContour.Type.UPPER_LIP_TOP
            FaceContour.UPPER_LIP_BOTTOM -> FaceData.FaceContour.Type.UPPER_LIP_BOTTOM
            FaceContour.LOWER_LIP_TOP -> FaceData.FaceContour.Type.LOWER_LIP_TOP
            FaceContour.LOWER_LIP_BOTTOM -> FaceData.FaceContour.Type.LOWER_LIP_BOTTOM
            FaceContour.NOSE_BRIDGE -> FaceData.FaceContour.Type.NOSE_BRIDGE
            FaceContour.NOSE_BOTTOM -> FaceData.FaceContour.Type.NOSE_BOTTOM
            FaceContour.LEFT_CHEEK -> FaceData.FaceContour.Type.LEFT_CHEEK
            FaceContour.RIGHT_CHEEK -> FaceData.FaceContour.Type.RIGHT_CHEEK
            else -> return null
        }
        return FaceData.FaceContour(
            type = type,
            points = points
        )
    }

    private fun FaceLandmark.toInternalModel(): FaceData.FaceLandmark? {
        val type = when (landmarkType) {
            FaceLandmark.MOUTH_BOTTOM -> FaceData.FaceLandmark.Type.MOUTH_BOTTOM
            FaceLandmark.MOUTH_RIGHT -> FaceData.FaceLandmark.Type.MOUTH_RIGHT
            FaceLandmark.MOUTH_LEFT -> FaceData.FaceLandmark.Type.MOUTH_LEFT
            FaceLandmark.RIGHT_EYE -> FaceData.FaceLandmark.Type.RIGHT_EYE
            FaceLandmark.LEFT_EYE -> FaceData.FaceLandmark.Type.LEFT_EYE
            FaceLandmark.RIGHT_EAR -> FaceData.FaceLandmark.Type.RIGHT_EAR
            FaceLandmark.LEFT_EAR -> FaceData.FaceLandmark.Type.LEFT_EAR
            FaceLandmark.RIGHT_CHEEK -> FaceData.FaceLandmark.Type.RIGHT_CHEEK
            FaceLandmark.LEFT_CHEEK -> FaceData.FaceLandmark.Type.LEFT_CHEEK
            FaceLandmark.NOSE_BASE -> FaceData.FaceLandmark.Type.NOSE_BASE
            else -> return null
        }

        return FaceData.FaceLandmark(
            type = type,
            position = position
        )
    }
}
