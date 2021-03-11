package com.aite.smartlib.manager

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import com.aite.smartlib.utils.Constants.Companion.GRAY_SERVICE_ID
import com.aite.smartlib.utils.startForegroundSafely
import com.aite.smartlib.utils.stopForegroundSafely

// 通知前台服务启动
internal class ForegroundManager(private val mService: Service) {
    fun setServiceForeground() {
        mService.startForegroundSafely(GRAY_SERVICE_ID, Notification())
    }

    fun cancelServiceForeground() {
        mService.stopForegroundSafely(true)
        (mService.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.cancel(GRAY_SERVICE_ID)
    }
}