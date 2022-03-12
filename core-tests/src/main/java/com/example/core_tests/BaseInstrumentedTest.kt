package com.example.core_tests

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

abstract class BaseInstrumentedTest {

    protected val targetContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext
}
