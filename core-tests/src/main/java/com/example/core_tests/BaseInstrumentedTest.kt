package com.example.core_tests

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import java.io.File

abstract class BaseInstrumentedTest {

    protected val targetContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @After
    fun cleanUpCache() {
        targetContext
            .cacheDir
            .listFiles()
            ?.forEach { it.deleteRecursively() }
    }

    protected fun getFileFromAssets(fileName: String): File {
        return targetContext
            .assets
            .open(fileName).use { inputStream ->
                File(targetContext.cacheDir, System.currentTimeMillis().toString()).apply {
                    outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
    }

    protected fun runOnUiThread(block: () -> Unit) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(block)
    }
}
