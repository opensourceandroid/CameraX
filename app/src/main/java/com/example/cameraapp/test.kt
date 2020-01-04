package com.example.cameraapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 *@date 2019/11/14
 *@des
 *@author hjy
 */

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

fun <T : String> allPermissionsGranted(REQUST_PERMISSIONS: Array<T>, context: Context) =
    REQUST_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
