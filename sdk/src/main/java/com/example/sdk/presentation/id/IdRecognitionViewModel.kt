package com.example.sdk.presentation.id

import androidx.lifecycle.viewModelScope
import com.example.core_ui.presentation.BaseViewModel
import com.example.sdk.data.model.id.TextDocumentResult
import com.example.sdk.domain.id.interactor.ExtractText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * Performs photo analysis for ID text extraction
 *
 * @property extractText [ExtractText] implementation
 */
internal class IdRecognitionViewModel @Inject constructor(
    private val extractText: ExtractText
) : BaseViewModel() {

    private val _documentResult = MutableSharedFlow<TextDocumentResult>()

    /**
     * Emits [TextDocumentResult] when the analysis is complete
     */
    val documentResult: Flow<TextDocumentResult> = _documentResult.asSharedFlow()

    fun onGotPhoto(file: File) {
        viewModelScope.launch {
            extractText
                .exec(file)
                .catch { t ->
                    Timber.e(t)

                    // TODO: Better error info
                    _documentResult.emit(TextDocumentResult.Failure(text = t.message.orEmpty()))
                }
                .collect { documentData ->
                    _documentResult.emit(TextDocumentResult.Success(documentData))
                }
        }
    }
}
