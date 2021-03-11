package com.aite.smartlib.service

import android.content.Intent
import com.aite.smartlib.logger
import com.aite.smartlib.manager.AudioManager
import com.aite.smartlib.manager.ForegroundManager
import com.aite.smartlib.manager.TimingManager
import com.aite.smartlib.manager.WakeUpBroadcastManager
import com.aite.smartlib.utils.AbstractServiceConnection
import com.aite.smartlib.utils.bindServiceSafely
import com.aite.smartlib.utils.startServiceSafely
import com.aite.smartlib.utils.unbindServiceSafely

/**
 * 用于保活应用的服务
 */
class RemoteService: LoggerService() {
    private val mForegroundManager by lazy {
        ForegroundManager(this)
    }

    private val mAudioManager by lazy {
        AudioManager(this)
    }

    private val mTimingManager by lazy {
        TimingManager(this)
    }
    
    private val mWakeUpBroadcastManager by lazy {
        WakeUpBroadcastManager(this)
    }

    private val mConn by lazy {
        AbstractServiceConnection()
    }

    override fun onCreate() {
        super.onCreate()
        mForegroundManager.setServiceForeground()
        mAudioManager.startMusic()
        mTimingManager.startJob()
        mWakeUpBroadcastManager.registerWakeUpReceiver()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger("绑定KeepLiveService")
        bindServiceSafely(Intent(this, KeepLiveService::class.java), mConn)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindServiceSafely(mConn)
        mAudioManager.stopMusic()
        mForegroundManager.cancelServiceForeground()
        mTimingManager.stopJob()
        mWakeUpBroadcastManager.unregisterWakeUpReceiver()
        // 调用stopKeepLiveService方法会杀不死服务，但是如果不关心服务的关闭，可取消注释。
       //        restart()
    }

    private fun restart() {
        startServiceSafely(Intent(this, RemoteService::class.java))
    }
}