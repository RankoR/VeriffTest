package com.example.sdk.data.model.face

import android.graphics.PointF
import android.graphics.Rect
import android.os.Parcelable
import com.example.sdk.data.model.face.FaceData.FaceContour
import com.example.sdk.data.model.face.FaceData.FaceLandmark
import kotlinx.parcelize.Parcelize
import kotlin.math.abs

/**
 * Wrapper object for the face detection data
 *
 * @property contours [List] of detected [FaceContour]s
 * @property landmarks [List] of detected [FaceLandmark]s
 * @property boundingBox Face's bounding box
 * @property headEulerAngleX Detected rotation of the head on the X axis
 * @property headEulerAngleY Detected rotation of the head on the Y axis
 * @property headEulerAngleZ Detected rotation of the head on the Z axis
 * @property leftEyeOpenProbability Probability of left eye open state, from 0f (closed) to 1f (open)
 * @property rightEyeOpenProbability Probability of right eye open state, from 0f (closed) to 1f (open)
 * @property smilingProbability Probability of smile on the face, from 0f (not smiling) to 1f (definitely smiling)
 */
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

    /**
     * True if average probability of both eyes open is above minimum value (0.2f)
     */
    val areBothEyesOpen: Boolean
        get() = (leftEyeOpenProbability + rightEyeOpenProbability) / 2 >= MIN_OPEN_EYE_PROBABILITY

    /**
     * True if head is not rotated too much (<15° by each axis)
     */
    val isHeadRotationValid: Boolean
        get() {
            return abs(headEulerAngleX) <= MAX_HEAD_ROTATION &&
                abs(headEulerAngleY) <= MAX_HEAD_ROTATION &&
                abs(headEulerAngleZ) <= MAX_HEAD_ROTATION
        }

    /**
     * Face contour
     *
     * @property type Contour [FaceContour.Type]
     * @property points List of contour points
     *
     * @see FaceContour.Type
     */
    @Parcelize
    data class FaceContour(
        val type: Type,
        val points: List<PointF>
    ) : Parcelable {

        /**
         * Type of [FaceContour]
         */
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

    /**
     * Face landmark
     *
     * @property type Landmark [FaceLandmark.Type]
     * @property position Position of the [FaceLandmark]
     *
     * @see FaceLandmark.Type
     */
    @Parcelize
    data class FaceLandmark(
        val type: Type,
        val position: PointF
    ) : Parcelable {

        /**
         * Type of [FaceLandmark]
         */
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
        private const val MIN_OPEN_EYE_PROBABILITY = 0.2f
        private const val MAX_HEAD_ROTATION = 15f
    }
}
