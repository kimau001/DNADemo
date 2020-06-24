package com.app.dnadetec.utills

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CheckPermission{
    companion object {
        private val reqPermission = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
        )

        const val REQUEST_CODE = 832

        @JvmStatic
        fun requestPermissions(mActivity: Activity) {
            ActivityCompat.requestPermissions(mActivity, reqPermission, REQUEST_CODE)
        }

        @JvmStatic
        fun isComplete(mActivity: Activity): Boolean {
            for (i in reqPermission.indices) {
                val check = ContextCompat.checkSelfPermission(mActivity, reqPermission[i])

                if (check != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }
    }
}