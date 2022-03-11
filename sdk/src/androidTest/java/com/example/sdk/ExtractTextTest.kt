@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.sdk

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.example.core_tests.assertThrows
import com.example.core_tests.awaitSingle
import com.example.sdk.domain.id.interactor.ExtractText
import com.example.sdk.domain.id.interactor.ExtractTextImpl
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
                .awaitSingle { data ->
                    assertEquals(data.toString(), 24, data.lineCount)
                }
        }
    }

    @Test
    fun normalExtractRealPhoto() {
        val idBitmap = getIdFromAssets("id1.jpeg")

        runTest {
            extractText
                .exec(idBitmap, 0)
                .awaitSingle { data ->
                    assertEquals(data.toString(), 24, data.lineCount)
                }
        }
    }

    @Test
    fun failedExtractEmptyImage() {
        val idBitmap = getIdFromAssets("empty.jpeg")

        runTest {
            extractText
                .exec(idBitmap, 0)
                .assertThrows<com.example.sdk.domain.id.exception.NoTextFoundException>()
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

    private val com.example.sdk.data.model.RawDocumentData.lineCount: Int
        get() {
            return blocks.fold(0) { count, block ->
                count + block.lines.size
            }
        }
}
