package com.example.sdk.data.model

import android.graphics.Rect
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawDocumentData(
    val blocks: List<Block>
) : Parcelable {

    @IgnoredOnParcel
    val isValid = blocks.isNotEmpty()

    override fun toString(): String {
        return blocks
            .asSequence()
            .map { it.lines }
            .flatten()
            .joinToString("\n")
    }

    @Parcelize
    data class Block(
        val lines: List<Line>,
        val language: String,
        val boundingBox: Rect?
    ) : Parcelable {

        @IgnoredOnParcel
        val isValid = lines.isNotEmpty()
    }

    @Parcelize
    data class Line(
        val text: String,
        val language: String,
        val boundingBox: Rect?
    ) : Parcelable {

        @IgnoredOnParcel
        val isValid = text.isNotBlank()
    }
}
