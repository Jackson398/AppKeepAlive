package com.aite.smart.service

import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.aite.smart.*
import com.aite.smart.manager.ForegroundManager
import com.aite.smart.manager.TimingManager
import com.aite.smart.utils.AbstractServiceConnection
import com.aite.smart.utils.SystemUtil

/**
 * 用于保活应用的服务
 */
class KeepLiveService : LoggerService() {
    private val connectedBlock = fun(name: ComponentName?, _: IBinder?) {
        logger("服务$name 连接上了")
        mTimingManager.stopJob()
    }

    private val disconnectedBlock = fun(name: ComponentName?) {
        logger("服务$name 断开了")
        mTimingManager.startJob() // 开启一个定时任务，每隔5s就检测一次服务连接状态
    }

    private val mTimingManager by lazy {
        TimingManager(this)
    }

    private val mForegroundManager by lazy {
        ForegroundManager(this)
    }

    private val mService by lazy {
        Intent().apply {
            component = ComponentName("at.smarthome.zh.gateway", "at.smarthome.aidl.GatewayAidlComm")
        }
    }

    private val mConn by lazy {
        AbstractServiceConnection().apply {
            onServiceConnected(connectedBlock = connectedBlock)
            onServiceDisConnected(disconnectedBlock = disconnectedBlock)
        }
    }

    override fun onCreate() {
        super.onCreate()
        mForegroundManager.startServiceForeground()
        startServiceSafely(mService)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bindServiceSafely(mService, mConn)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindServiceSafely(mConn)
        mForegroundManager.cancelServiceForeground()
    }
}