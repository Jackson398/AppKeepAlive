package com.aite.smartlib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.aite.smartlib.service.KeepLiveService
import com.aite.smartlib.service.RemoteService
import com.aite.smartlib.service.TimingService
import com.aite.smartlib.utils.UtilFunction
import com.aite.smartlib.utils.startForegroundServiceSafely
import com.aite.smartlib.utils.startServiceSafely
import com.aite.smartlib.utils.stopServiceSafely

@SuppressLint("StaticFieldLeak")
private var mContext: Context? = null

// 紧跟着Application的super.onCreate()下调用。
fun init(context: Context): Boolean {
    mContext = context.applicationContext
    return UtilFunction.isMainProcess(context)
}

// 在主进程中调用
fun startKeepLiveService() {
    val context = mContext.assertNull()
    if (UtilFunction.isMainProcess(context)) {
        val intent = Intent(context, KeepLiveService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundServiceSafely(intent)
        } else {
            context.startServiceSafely(intent)
        }
    }
}

// 在主进程中调用。
fun stopKeepLiveService() {
    val context = mContext.assertNull()
    if (UtilFunction.isMainProcess(context)) {
        context.apply {
            stopServiceSafely(Intent(this, TimingService::class.java))
            stopServiceSafely(Intent(this, RemoteService::class.java))
            stopServiceSafely(Intent(this, KeepLiveService::class.java))
        }
    }
}

internal fun logger(message: String) {
    val context = mContext.assertNull()
    if (UtilFunction.isDebug(context)) {
        Log.d("KeepLive", message)
    }
}

private fun Context?.assertNull(): Context {
    return this ?: throw IllegalStateException("please init first")
}