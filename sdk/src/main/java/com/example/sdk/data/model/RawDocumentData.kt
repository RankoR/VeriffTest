package com.example.sdk.data.model

import android.graphics.Rect

data class RawDocumentData(
    val blocks: List<Block>
) {

    val isValid = blocks.isNotEmpty()

    override fun toString(): String {
        return blocks
            .asSequence()
            .map { it.lines }
            .flatten()
            .joinToString("\n")
    }

    data class Block(
        val lines: List<Line>,
        val language: String,
        val boundingBox: Rect?
    ) {
        val isValid = lines.isNotEmpty()
    }

    data class Line(
        val text: String,
        val language: String,
        val boundingBox: Rect?
    ) {
        val isValid = text.isNotBlank()
    }
}
