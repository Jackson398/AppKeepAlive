package com.aite.smartlib.service

import android.content.Intent
import com.aite.smartlib.manager.ForegroundManager
import com.aite.smartlib.manager.ScreenManager
import com.aite.smartlib.utils.AbstractServiceConnection
import com.aite.smartlib.utils.bindServiceSafely
import com.aite.smartlib.utils.startServiceSafely
import com.aite.smartlib.utils.unbindServiceSafely

class KeepLiveService : LoggerService() {
    private val mForegroundManager by lazy {
        ForegroundManager(this)
    }

    private val mScreenManager by lazy {
        ScreenManager(this)
    }

    private val mService by lazy {
        Intent(this, RemoteService::class.java)
    }

    private val mConn by lazy {
        AbstractServiceConnection()
    }

    override fun onCreate() {
        super.onCreate()
        mForegroundManager.setServiceForeground()
        mScreenManager.registerScreenReceiver()
        startServiceSafely(mService) // 应用服务被kill后，该进程会执行这里将应用重启
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bindServiceSafely(mService, mConn)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindServiceSafely(mConn)
        mScreenManager.unregisterScreenReceiver()
        mForegroundManager.cancelServiceForeground()
    }
}