@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.sdk

import com.example.core_tests.BaseInstrumentedTest
import com.example.core_tests.assertThrows
import com.example.core_tests.awaitSingle
import com.example.sdk.data.model.RawDocumentData
import com.example.sdk.domain.id.exception.NoTextFoundException
import com.example.sdk.domain.id.interactor.ExtractText
import com.example.sdk.domain.id.interactor.ExtractTextImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class ExtractTextTest : BaseInstrumentedTest() {

    private val extractText: ExtractText = ExtractTextImpl(
        targetContext,
        Dispatchers.Default
    )

    @Test
    fun normalExtractScannedId() {
        val idBitmap = getIdFromAssets("id0.jpeg")

        runTest {
            extractText
                .exec(idBitmap)
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
                .exec(idBitmap)
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
                .exec(idBitmap)
                .assertThrows<NoTextFoundException>()
        }
    }

    private fun getIdFromAssets(fileName: String): File {
        return targetContext
            .assets
            .open("images/$fileName").use { inputStream ->
                File(targetContext.cacheDir, System.currentTimeMillis().toString()).apply {
                    outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
    }

    private val RawDocumentData.lineCount: Int
        get() {
            return blocks.fold(0) { count, block ->
                count + block.lines.size
            }
        }
}
