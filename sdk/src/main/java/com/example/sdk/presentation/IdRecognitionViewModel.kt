package com.example.sdk.presentation

import com.example.core_ui.presentation.BaseViewModel
import com.example.text_detection.domain.ExtractText
import javax.inject.Inject

class IdRecognitionViewModel @Inject constructor(
    private val extractText: ExtractText
) : BaseViewModel()
