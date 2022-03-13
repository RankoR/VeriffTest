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

    @Parcelize
    data class Failure(
        val text: String
    ) : FaceResult()

    @Parcelize
    object Cancelled : FaceResult()
}
