package com.example.testapp

import android.app.Application
import com.example.sdk.VeriffSdk

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        VeriffSdk.initialize(this)
    }
}
