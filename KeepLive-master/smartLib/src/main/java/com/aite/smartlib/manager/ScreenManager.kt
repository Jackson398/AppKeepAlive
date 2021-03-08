package com.aite.smartlib.manager

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.aite.smartlib.activity.OnePixelActivity
import com.aite.smartlib.logger
import com.aite.smartlib.utils.AbstractReceiver
import com.aite.smartlib.utils.Constants.Companion.ACTION_FINISH_ONE_PIXEL_ACTIVITY
import com.aite.smartlib.utils.registerReceiverSafely
import com.aite.smartlib.utils.unregisterReceiverSafely

internal class ScreenManager(private val mContext: Context) {
    private val mScreenReceiver by lazy {
        AbstractReceiver().onReceive { _, intent ->
            intent?.action?.let {
                when (it) {
                    Intent.ACTION_SCREEN_ON -> finishOnePixelActivity()
                    Intent.ACTION_SCREEN_OFF -> startOnePixelActivity()
                    Intent.ACTION_USER_PRESENT -> {
                        // 解锁，暂时不用，保留
                    }
                }
            }
            Unit
        }
    }

    private fun startOnePixelActivity() {
        logger("开启一像素")
        mContext.startActivity(Intent(mContext, OnePixelActivity::class.java))
    }

    private fun finishOnePixelActivity() {
        logger("关闭一像素")
        mContext.sendBroadcast(Intent(ACTION_FINISH_ONE_PIXEL_ACTIVITY))
    }

    fun registerScreenReceiver() {
        mContext.registerReceiverSafely(mScreenReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_USER_PRESENT)
        })
    }

    fun unregisterScreenReceiver() {
        mContext.unregisterReceiverSafely(mScreenReceiver)
    }
}