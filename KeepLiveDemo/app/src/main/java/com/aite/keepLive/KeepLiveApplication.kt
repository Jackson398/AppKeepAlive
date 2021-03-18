package com.aite.keepLive

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

class KeepLiveApplication : Application() {
    companion object {
        var mContext: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        KeepLiveInit.initAppsKeepLive()
    }
}

fun isDebug(context: Context): Boolean = try {
    (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
} catch (e: Exception) {
    false
}

fun logger(message: String) {
    val context = KeepLiveApplication.mContext
    if (isDebug(context!!)) {
        Log.d("KeepLive", message)
    }
}
