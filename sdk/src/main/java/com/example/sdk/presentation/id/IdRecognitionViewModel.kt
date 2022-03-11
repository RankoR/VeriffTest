package com.example.sdk.presentation.id

import com.example.core_ui.presentation.BaseViewModel
import com.example.sdk.domain.id.interactor.ExtractText
import javax.inject.Inject

class IdRecognitionViewModel @Inject constructor(
    private val extractText: ExtractText
) : BaseViewModel()
