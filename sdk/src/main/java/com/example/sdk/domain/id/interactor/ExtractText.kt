package com.example.sdk.domain.id.interactor

import android.graphics.Bitmap
import com.example.sdk.data.model.RawDocumentData
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.Text.TextBlock
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

interface ExtractText {
    suspend fun exec(bitmap: Bitmap, rotationDegree: Int): Flow<RawDocumentData>
}

internal class ExtractTextImpl(
    private val coroutineDispatcher: CoroutineDispatcher
) : ExtractText {

    private val textRecognizer by lazy { TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS) }

    override suspend fun exec(bitmap: Bitmap, rotationDegree: Int): Flow<RawDocumentData> {
        return callbackFlow {
            val inputImage = InputImage.fromBitmap(bitmap, rotationDegree)

            textRecognizer
                .process(inputImage)
                .addOnFailureListener { t -> close(t) }
                .addOnSuccessListener { text ->
                    text
                        .textBlocks
                        .mapNotNull { textBlock -> textBlock.toInternalBlock() }
                        .let(::RawDocumentData)
                        .let { rawDocumentData ->
                            if (rawDocumentData.isValid) {
                                trySend(rawDocumentData)
                            } else {
                                close(com.example.sdk.domain.id.exception.NoTextFoundException())
                            }
                        }
                }

            awaitClose {
                // Unfortunately it's not cancellable
            }
        }.flowOn(coroutineDispatcher)
    }

    /**
     * Converting MLKit's model to internal model to get rid of external dependency on the upper levels
     * Also useful for cases when we use different implementations
     */
    private fun TextBlock.toInternalBlock(): RawDocumentData.Block? {
        return RawDocumentData.Block(
            lines = this.lines.mapNotNull { line -> line.toInternalLine() },
            language = recognizedLanguage,
            boundingBox = boundingBox
        ).takeIf { it.isValid }
    }

    private fun Text.Line.toInternalLine(): RawDocumentData.Line? {
        return RawDocumentData.Line(
            text = text,
            language = recognizedLanguage,
            boundingBox = boundingBox
        ).takeIf { it.isValid }
    }
}
