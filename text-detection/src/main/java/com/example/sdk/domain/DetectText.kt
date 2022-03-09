package com.example.text_detection.domain

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.Flow

interface DetectText {
    suspend fun exec(bitmap: Bitmap, rotationDegree: Int): Flow<List<String>>
}

internal class DetectTextImpl : DetectText {

    override suspend fun exec(bitmap: Bitmap, rotationDegree: Int): Flow<List<String>> {
        InputImage.fromBitmap(bitmap, rotationDegree)
        TODO("Not yet implemented")
    }
}
