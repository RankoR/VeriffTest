package com.example.sdk.presentation

import androidx.activity.viewModels
import com.example.core_ui.presentation.BaseActivity
import com.example.sdk.databinding.ActivityIdRecognitionBinding

class IdRecognitionActivity : BaseActivity<ActivityIdRecognitionBinding>(ActivityIdRecognitionBinding::inflate) {

    override val viewModel: IdRecognitionViewModel by viewModels()
}
