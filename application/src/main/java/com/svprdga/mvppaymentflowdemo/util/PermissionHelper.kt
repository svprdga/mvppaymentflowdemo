package com.svprdga.mvppaymentflowdemo.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

class PermissionHelper(
    private val context: Context) {

    val isReadContactsPermisionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
}