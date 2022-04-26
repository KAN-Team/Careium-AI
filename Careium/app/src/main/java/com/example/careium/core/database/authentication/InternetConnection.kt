package com.example.careium.core.database.authentication

import android.content.Context
import android.net.ConnectivityManager

class InternetConnection {

    companion object {
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected())
                return true

            return false
        }
    }
}