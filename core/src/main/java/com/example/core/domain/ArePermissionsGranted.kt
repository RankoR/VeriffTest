package com.example.core.domain

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Check if given permissions are granted
 */
interface ArePermissionsGranted {

    /**
     * Check if all permissions are granted
     *
     * @param permissions Permissions to check
     *
     * @return True if all permissions are granted
     */
    fun exec(vararg permissions: String): Boolean
}

/**
 * Implementation of permissions check
 *
 * @param context [Context] to use (any context)
 */
internal class ArePermissionsGrantedImpl(
    private val context: Context
) : ArePermissionsGranted {

    override fun exec(vararg permissions: String): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}
