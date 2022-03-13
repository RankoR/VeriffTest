package com.example.sdk.presentation.face

import androidx.lifecycle.viewModelScope
import com.example.core_ui.presentation.BaseViewModel
import com.example.sdk.data.model.face.FaceResult
import com.example.sdk.domain.face.interactor.DetectFace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class FaceRecognitionViewModel @Inject constructor(
    private val detectFace: DetectFace
) : BaseViewModel() {

    private val _faceResult = MutableSharedFlow<FaceResult>()
    val faceResult: Flow<FaceResult> = _faceResult.asSharedFlow()

    fun onGotPhoto(file: File) {
        viewModelScope.launch {
            detectFace
                .exec(file)
                .catch { t ->
                    Timber.e(t)

                    // TODO: Better error info
                    _faceResult.emit(FaceResult.Failure(text = t.message.orEmpty()))
                }
                .collect { faceData ->
                    _faceResult.emit(
                        FaceResult.Success(
                            photoFile = file,
                            faceData = faceData
                        )
                    )
                }
        }
    }
}
