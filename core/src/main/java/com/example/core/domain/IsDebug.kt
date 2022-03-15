package com.example.core.domain

import com.example.core.BuildConfig

/**
 * Checks if it's a debug build
 */
interface IsDebug {

    /**
     * Execute the debug build check
     *
     * @return True if current build is debug
     */
    fun exec(): Boolean
}

/**
 * Debug build check implementation
 *
 * Uses [BuildConfig.DEBUG] value
 */
internal class IsDebugImpl : IsDebug {

    override fun exec(): Boolean {
        return BuildConfig.DEBUG
    }
}
