@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.text_detection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.example.core_tests.assertThrows
import com.example.core_tests.awaitSingle
import com.example.text_detection.domain.ExtractText
import com.example.text_detection.domain.ExtractTextImpl
import com.example.text_detection.domain.exception.NoTextFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtractTextTest {

    private val extractText: ExtractText = ExtractTextImpl(Dispatchers.Default)

    @Test
    fun normalExtractScannedId() {
        val idBitmap = getIdFromAssets("id0.jpeg")

        runTest {
            extractText
                .exec(idBitmap, 0)
                .awaitSingle { lines ->
                    assertEquals(lines.toString(), 24, lines.size)
                }
        }
    }

    @Test
    fun normalExtractRealPhoto() {
        val idBitmap = getIdFromAssets("id1.jpeg")

        runTest {
            extractText
                .exec(idBitmap, 0)
                .awaitSingle { lines ->
                    assertEquals(lines.toString(), 24, lines.size)
                }
        }
    }

    @Test
    fun failedExtractEmptyImage() {
        val idBitmap = getIdFromAssets("empty.jpeg")

        runTest {
            extractText
                .exec(idBitmap, 0)
                .assertThrows<NoTextFoundException>()
        }
    }

    private fun getIdFromAssets(fileName: String): Bitmap {
        return InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .assets
            .open("images/$fileName").use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
    }
}
