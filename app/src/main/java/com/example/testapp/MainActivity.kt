package com.example.testapp

import android.os.Bundle
import com.example.core_ui.presentation.BaseActivity
import com.example.core_ui.util.setOnSingleClickListener
import com.example.sdk.VeriffSdk
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.readTextBtn.setOnSingleClickListener {
            VeriffSdk
                .createIdRecognitionIntent(this)
                .let { intent ->
                    startActivity(intent)
                }
        }
    }
}
