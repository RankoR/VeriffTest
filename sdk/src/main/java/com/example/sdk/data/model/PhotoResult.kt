package com.example.sdk.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Result of taking photo
 */
internal sealed class PhotoResult : Parcelable {

    /**
     * Photo taken successfully
     *
     * @property filePath Path of the saved photo
     */
    @Parcelize
    data class Success(
        val filePath: String
    ) : PhotoResult()

    /**
     * Photo was not taken
     *
     * @property errorMessage Error message
     */
    @Parcelize
    data class Failure(
        val errorMessage: String
    ) : PhotoResult()
}
