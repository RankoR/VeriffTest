package com.example.sdk.data.model.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Result of the text detection
 *
 * @see RawDocumentData
 */
sealed class TextDocumentResult : Parcelable {

    /**
     * Text detection was successful: found at least one line of text
     *
     * @property documentData [RawDocumentData] with detected text
     *
     * TODO: Add file with photo?
     *
     * @see RawDocumentData
     */
    @Parcelize
    data class Success(
        val documentData: RawDocumentData
    ) : TextDocumentResult()

    /**
     * Text detection failed, possible reason is that no text found on the image
     *
     * @property text Error message
     *
     * TODO: Returning just a text is not the best solution.
     * TODO: We should better return some kind of error class, like enum class FailureType: NoText
     */
    @Parcelize
    data class Failure(
        val text: String
    ) : TextDocumentResult()

    /**
     * Text detection was cancelled
     */
    @Parcelize
    object Cancelled : TextDocumentResult()
}
