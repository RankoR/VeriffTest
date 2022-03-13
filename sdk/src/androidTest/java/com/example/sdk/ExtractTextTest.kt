@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.sdk

import com.example.core_tests.BaseInstrumentedTest
import com.example.core_tests.assertThrows
import com.example.core_tests.awaitSingle
import com.example.sdk.data.model.id.RawDocumentData
import com.example.sdk.domain.id.exception.NoTextFoundException
import com.example.sdk.domain.id.interactor.ExtractText
import com.example.sdk.domain.id.interactor.ExtractTextImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtractTextTest : BaseInstrumentedTest() {

    private val extractText: ExtractText = ExtractTextImpl(
        targetContext,
        Dispatchers.Default
    )

    @Test
    fun normalExtractScannedId() {
        val file = getFileFromAssets("images/ids/id0.jpeg")

        runTest {
            extractText
                .exec(file)
                .awaitSingle { data ->
                    assertEquals(data.toString(), 24, data.lineCount)
                }
        }
    }

    @Test
    fun normalExtractRealPhoto() {
        val file = getFileFromAssets("images/ids/id1.jpeg")

        runTest {
            extractText
                .exec(file)
                .awaitSingle { data ->
                    assertEquals(data.toString(), 24, data.lineCount)
                }
        }
    }

    @Test
    fun failedExtractEmptyImage() {
        val file = getFileFromAssets("images/ids/empty.jpeg")

        runTest {
            extractText
                .exec(file)
                .assertThrows<NoTextFoundException>()
        }
    }

    private val RawDocumentData.lineCount: Int
        get() {
            return blocks.fold(0) { count, block ->
                count + block.lines.size
            }
        }
}
