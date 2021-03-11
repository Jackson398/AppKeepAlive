package com.aite.smart.manager

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import com.aite.smart.constants.Constants
import com.aite.smart.startForegroundSafely
import com.aite.smart.stopForegroundSafely

internal class ForegroundManager(private val mService: Service) {
    fun startServiceForeground() {
        mService.startForegroundSafely(Constants.GRAY_SERVICE_ID, Notification())
    }

    fun cancelServiceForeground() {
        mService.stopForegroundSafely(true)
        (mService.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.cancel(Constants.GRAY_SERVICE_ID)
    }
}