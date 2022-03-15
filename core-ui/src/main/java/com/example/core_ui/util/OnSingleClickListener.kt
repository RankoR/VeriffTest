package com.example.core_ui.util

import android.os.SystemClock
import android.view.View

/**
 * Prevents multiple repeating clicks on view
 */
private class OnSingleClickListener(
    private val block: () -> Unit,
    private val delay: Long = DEFAULT_DELAY_MS
) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastClickTime < delay) {
            return
        }

        lastClickTime = SystemClock.elapsedRealtime()

        block()
    }

    companion object {
        const val DEFAULT_DELAY_MS = 1000L
    }
}

/**
 * Acts like a usual click listener, but prevents repeating clicks
 */
fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}
