package com.example.sdk.data.model.id

import android.graphics.Rect
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Raw document text data consisting of text blocks
 *
 * @property blocks List of text blocks
 *
 * @see Block
 * @see Line
 */
@Parcelize
data class RawDocumentData(
    val blocks: List<Block>
) : Parcelable {

    /**
     * Document data is valid if we have at least one block
     */
    @IgnoredOnParcel
    val isValid = blocks.isNotEmpty()

    /**
     * Concatenates all lines with a \n
     */
    override fun toString(): String {
        return blocks
            .asSequence()
            .map { block -> block.lines.map { line -> line.text } }
            .flatten()
            .joinToString("\n")
    }

    /**
     * Text block
     *
     * @property lines List of text lines
     * @property language Detected language of the block in ISO 639-1
     * @property boundingBox Bounding box of the block on the original image. Can be null
     *
     * @see Line
     */
    @Parcelize
    data class Block(
        val lines: List<Line>,
        val language: String,
        val boundingBox: Rect?
    ) : Parcelable {

        /**
         * Block is valid if contains at least one line
         */
        @IgnoredOnParcel
        val isValid = lines.isNotEmpty()
    }

    /**
     * Text line
     *
     * @property text Detected text, never blank
     * @property language Detected language of the line in ISO 639-1
     * @property boundingBox Bounding box of the line on the original image. Can be null
     */
    @Parcelize
    data class Line(
        val text: String,
        val language: String,
        val boundingBox: Rect?
    ) : Parcelable {

        /**
         * Line is valid if it is not blank
         */
        @IgnoredOnParcel
        val isValid = text.isNotBlank()
    }
}
