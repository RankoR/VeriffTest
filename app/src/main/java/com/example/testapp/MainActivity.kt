package com.example.testapp

import android.os.Bundle
import com.example.core_ui.presentation.BaseActivity
import com.example.core_ui.util.setOnSingleClickListener
import com.example.sdk.VeriffSdk
import com.example.testapp.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeVeriffSdk()

        binding.readTextBtn.setOnSingleClickListener {
            VeriffSdk.launchIdRecognition()
        }
    }

    private fun initializeVeriffSdk() {
        VeriffSdk.registerActivity(this)
        VeriffSdk.onTextDocumentResult = { result ->
            Timber.d("Text document result: $result")
        }
    }
}
