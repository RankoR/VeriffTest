package com.example.sdk.data.model.face

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

sealed class FaceResult : Parcelable {

    @Parcelize
    data class Success(
        val photoFile: File,
        val faceData: FaceData
    ) : FaceResult()

    /**
     * TODO: Returning just a text is not the best solution.
     * We should better return some kind of error class, like enum class FailureType: MissingFace|MultipleFaces|etc
     */
    @Parcelize
    data class Failure(
        val text: String
    ) : FaceResult()

    @Parcelize
    object Cancelled : FaceResult()
}
