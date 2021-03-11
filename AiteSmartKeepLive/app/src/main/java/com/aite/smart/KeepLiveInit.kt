package com.aite.smart

import android.content.Context
import android.content.Intent
import android.os.Build
import com.aite.smart.service.KeepLiveService

object KeepLiveInit {
    private var mContext: Context? = null

    fun init(context: Context): Boolean {
        logger("===KeepLiveInit===")
        mContext = context.applicationContext
        return isMainProcess(context)
    }

    fun startKeepLiveService() {
        logger("====startKeepLiveService===")
        val context = mContext.assertNull()
        if (isMainProcess(context)) {
            val intent = Intent(context, KeepLiveService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundServiceSafely(intent)
            } else {
                context.startServiceSafely(intent)
            }
        }
    }

    private fun Context?.assertNull(): Context {
        return this ?: throw IllegalStateException("please init first")
    }
}