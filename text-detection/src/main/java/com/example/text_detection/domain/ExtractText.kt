package com.example.text_detection.domain

import android.graphics.Bitmap
import com.example.text_detection.domain.exception.NoTextFoundException
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

interface ExtractText {
    suspend fun exec(bitmap: Bitmap, rotationDegree: Int): Flow<List<String>>
}

internal class ExtractTextImpl(
    private val coroutineDispatcher: CoroutineDispatcher
) : ExtractText {

    private val textRecognizer by lazy { TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS) }

    override suspend fun exec(bitmap: Bitmap, rotationDegree: Int): Flow<List<String>> {
        return callbackFlow {
            val inputImage = InputImage.fromBitmap(bitmap, rotationDegree)

            textRecognizer
                .process(inputImage)
                .addOnFailureListener { t -> close(t) }
                .addOnSuccessListener { text ->
                    text
                        .textBlocks
                        .map { textBlock ->
                            textBlock
                                .lines
                                .mapNotNull { line ->
                                    line.text.takeIf { it.isNotBlank() }
                                }
                        }
                        .flatten()
                        .let { lines ->
                            if (lines.isNotEmpty()) {
                                trySend(lines)
                            } else {
                                close(NoTextFoundException())
                            }
                        }
                }

            awaitClose {
                // Unfortunately it's not cancellable
            }
        }.flowOn(coroutineDispatcher)
    }
}
