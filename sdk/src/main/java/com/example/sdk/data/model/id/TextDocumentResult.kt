package com.example.sdk.data.model.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class TextDocumentResult : Parcelable {

    @Parcelize
    data class Success(
        val documentData: RawDocumentData
    ) : TextDocumentResult()

    @Parcelize
    data class Failure(
        val text: String
    ) : TextDocumentResult()

    @Parcelize
    object Cancelled : TextDocumentResult()
}
