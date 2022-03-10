package com.example.sdk.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.core_ui.presentation.BaseActivity
import com.example.sdk.databinding.ActivityIdRecognitionBinding
import com.example.sdk.di.DiHolder
import javax.inject.Inject

class IdRecognitionActivity : BaseActivity<ActivityIdRecognitionBinding>(ActivityIdRecognitionBinding::inflate) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: IdRecognitionViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializedDi()
    }

    private fun initializedDi() {
        DiHolder.sdkComponent.inject(this)
    }
}
