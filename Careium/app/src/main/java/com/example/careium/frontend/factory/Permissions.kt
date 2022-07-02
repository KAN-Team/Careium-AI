package com.example.careium.frontend.factory

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions(private var context: Context, private var activity: Activity) :
    AppCompatActivity() {

    companion object {
        const val READ_EXTERNAL_STORAGE = 2
    }

    fun checkPermissions(Permission: String, requestCode: Int): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this.context, Permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(activity, arrayOf(Permission), requestCode)
            false
        } else
            true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.context, "Storage Permission Granted", Toast.LENGTH_LONG).show()
            }
        }
    }
}