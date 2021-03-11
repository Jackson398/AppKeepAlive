package com.aite.smart.service

import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.aite.smart.*
import com.aite.smart.manager.ForegroundManager
import com.aite.smart.utils.AbstractServiceConnection
import com.aite.smart.utils.SystemUtil

/**
 * 用于保活应用的服务
 */
class KeepLiveService : LoggerService() {
    companion object {
        const val DISCONNECTED_TIMEOUT = 10 * 1000L
        const val DISCONNECTED_MSG = 1
    }
    private inner class InnerHandler: Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == DISCONNECTED_MSG) {
                // 网关APP被强制关闭，10s内连接还是断开状态则重启网关
                logger("时间到，重启网关")
                SystemUtil.restartGateway(KeepLiveApplication.getContext(), 1000)
            }
            super.handleMessage(msg)
        }
    }

    private var mHandler = InnerHandler()

    private val connectedBlock = fun(name: ComponentName?, _: IBinder?) {
        logger("服务$name 连接上了")
        mHandler.removeCallbacksAndMessages(null)
    }

    private val disconnectedBlock = fun(name: ComponentName?) {
        logger("服务$name 断开了")
        mHandler.sendEmptyMessageDelayed(DISCONNECTED_MSG, DISCONNECTED_TIMEOUT)
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
            onServiceDisConnected(disconnectedBlock)
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