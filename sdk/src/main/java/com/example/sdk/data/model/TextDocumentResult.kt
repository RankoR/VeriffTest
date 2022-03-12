package com.example.sdk.data.model

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
    class Cancelled : TextDocumentResult()
}
