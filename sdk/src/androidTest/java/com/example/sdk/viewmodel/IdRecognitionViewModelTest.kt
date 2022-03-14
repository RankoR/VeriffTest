@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.sdk.viewmodel

import android.graphics.Rect
import com.example.core_tests.BaseInstrumentedTest
import com.example.core_tests.awaitSingle
import com.example.sdk.data.model.id.RawDocumentData
import com.example.sdk.data.model.id.TextDocumentResult
import com.example.sdk.domain.id.exception.NoTextFoundException
import com.example.sdk.domain.id.interactor.ExtractText
import com.example.sdk.presentation.id.IdRecognitionViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertIs

class IdRecognitionViewModelTest : BaseInstrumentedTest() {

    private val extractText: ExtractText = mockk()
    private val viewModel = IdRecognitionViewModel(
        extractText = extractText
    )

    @Test
    fun normalDetection() {
        val rawDocumentData = RawDocumentData(
            blocks = listOf(
                RawDocumentData.Block(
                    lines = listOf(
                        RawDocumentData.Line("Hello", language = "en", boundingBox = Rect()),
                        RawDocumentData.Line("Hello", language = "en", boundingBox = Rect()),
                    ),
                    language = "en",
                    boundingBox = Rect()
                )
            )
        )

        coEvery { extractText.exec(any()) } returns flowOf(rawDocumentData)

        viewModel.onGotPhoto(File("/"))

        runTest {
            viewModel
                .documentResult
                .awaitSingle { result ->
                    assertIs<TextDocumentResult.Success>(result)

                    assertEquals(rawDocumentData, result.documentData)

                    coVerify { extractText.exec(File("/")) }
                    confirmVerified(extractText)
                }
        }
    }

    @Test
    fun errorInDetection() {
        coEvery { extractText.exec(any()) } returns flow { throw NoTextFoundException() }

        viewModel.onGotPhoto(File("/"))

        runTest {
            viewModel
                .documentResult
                .awaitSingle { result ->
                    assertIs<TextDocumentResult.Failure>(result)

                    assertEquals("No text found", result.text)

                    coVerify { extractText.exec(File("/")) }
                    confirmVerified(extractText)
                }
        }
    }
}
