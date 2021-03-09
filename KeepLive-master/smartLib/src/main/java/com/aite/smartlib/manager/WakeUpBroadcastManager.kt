package com.aite.smartlib.manager

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.aite.smartlib.logger
import com.aite.smartlib.service.KeepLiveService
import com.aite.smartlib.utils.AbstractReceiver
import com.aite.smartlib.utils.registerReceiverSafely
import com.aite.smartlib.utils.startServiceSafely
import com.aite.smartlib.utils.unregisterReceiverSafely

class WakeUpBroadcastManager(private val mContext: Context) {
    private val mScreenReceiver by lazy {
        AbstractReceiver().onReceive { _, intent ->
            intent?.action?.let {
                logger("收到唤醒广播:${it}")
                startKeepLiveService()
            }
        }
    }

    private fun startKeepLiveService() {
        mContext.startServiceSafely(Intent(mContext, KeepLiveService::class.java))
    }

    fun registerWakeUpReceiver() {
        mContext.registerReceiverSafely(mScreenReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_USER_PRESENT)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_BOOT_COMPLETED)
            addAction(Intent.ACTION_MEDIA_MOUNTED)
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        })
    }

    fun unregisterWakeUpReceiver() {
        mContext.unregisterReceiverSafely(mScreenReceiver)
    }
}