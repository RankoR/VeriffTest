package com.example.core.domain

import com.example.core.BuildConfig

interface IsDebug {
    fun exec(): Boolean
}

internal class IsDebugImpl : IsDebug {

    override fun exec(): Boolean {
        return BuildConfig.DEBUG
    }
}
