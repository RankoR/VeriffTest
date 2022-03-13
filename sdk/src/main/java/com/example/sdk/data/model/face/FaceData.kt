package com.example.sdk.data.model.face

import android.graphics.PointF
import android.graphics.Rect
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.abs

@Parcelize
data class FaceData(
    val contours: List<FaceContour>,
    val landmarks: List<FaceLandmark>,
    val boundingBox: Rect,
    val headEulerAngleX: Float,
    val headEulerAngleY: Float,
    val headEulerAngleZ: Float,
    val leftEyeOpenProbability: Float,
    val rightEyeOpenProbability: Float,
    val smilingProbability: Float
) : Parcelable {

    val areBothEyesOpen: Boolean
        get() = leftEyeOpenProbability >= MIN_OPEN_EYE_PROBABILITY && rightEyeOpenProbability >= MIN_OPEN_EYE_PROBABILITY

    val isHeadRotationValid: Boolean
        get() {
            return abs(headEulerAngleX) <= MAX_HEAD_ROTATION &&
                abs(headEulerAngleY) <= MAX_HEAD_ROTATION &&
                abs(headEulerAngleZ) <= MAX_HEAD_ROTATION
        }

    @Parcelize
    data class FaceContour(
        val type: Type,
        val points: List<PointF>
    ) : Parcelable {

        enum class Type {
            FACE,
            LEFT_CHEEK,
            LEFT_EYE,
            LEFT_EYEBROW_BOTTOM,
            LEFT_EYEBROW_TOP,
            LOWER_LIP_BOTTOM,
            LOWER_LIP_TOP,
            NOSE_BOTTOM,
            NOSE_BRIDGE,
            RIGHT_CHEEK,
            RIGHT_EYE,
            RIGHT_EYEBROW_BOTTOM,
            RIGHT_EYEBROW_TOP,
            UPPER_LIP_BOTTOM,
            UPPER_LIP_TOP
        }
    }

    @Parcelize
    data class FaceLandmark(
        val type: Type,
        val position: PointF
    ) : Parcelable {

        enum class Type {
            LEFT_CHEEK,
            LEFT_EAR,
            LEFT_EYE,
            MOUTH_BOTTOM,
            MOUTH_LEFT,
            MOUTH_RIGHT,
            NOSE_BASE,
            RIGHT_CHEEK,
            RIGHT_EAR,
            RIGHT_EYE
        }
    }

    private companion object {
        private const val MIN_OPEN_EYE_PROBABILITY = 0.7f
        private const val MAX_HEAD_ROTATION = 15f
    }
}
