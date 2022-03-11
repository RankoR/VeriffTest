package com.example.core.domain

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

interface ArePermissionsGranted {
    fun exec(vararg permissions: String): Boolean
}

internal class ArePermissionsGrantedImpl(
    private val context: Context
) : ArePermissionsGranted {

    override fun exec(vararg permissions: String): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}
