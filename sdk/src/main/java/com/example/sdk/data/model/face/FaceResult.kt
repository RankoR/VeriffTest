package com.example.sdk.data.model.face

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

/**
 * Result of the face detection
 *
 * @see FaceData
 */
sealed class FaceResult : Parcelable {

    /**
     * Face detection was successful: found exactly one face, head is not rotated too much, eyes are open
     *
     * @property photoFile [File] containing the photo
     * @property faceData [FaceData] containing the face detection information
     */
    @Parcelize
    data class Success(
        val photoFile: File,
        val faceData: FaceData
    ) : FaceResult()

    /**
     * Face detection failed, probable reasons: no faces, multiple faces, eyes closed, head rotated too much
     *
     * @property text Error message
     *
     * TODO: Returning just a text is not the best solution.
     *
     * TODO: We should better return some kind of error class, like enum class FailureType: MissingFace|MultipleFaces|etc
     */
    @Parcelize
    data class Failure(
        val text: String
    ) : FaceResult()

    /**
     * Face detection was cancelled
     */
    @Parcelize
    object Cancelled : FaceResult()
}
