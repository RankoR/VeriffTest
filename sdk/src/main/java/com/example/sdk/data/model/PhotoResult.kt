package com.example.sdk.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class PhotoResult : Parcelable {

    @Parcelize
    data class Success(
        val filePath: String
    ) : PhotoResult()

    @Parcelize
    data class Failure(
        val errorMessage: String
    ) : PhotoResult()
}