package com.aite.smart.service

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.aite.smart.IProcessService

/**
 * 远程服务
 */
class RemoteService : Service() {
    companion object {
        const val TAG = "RemoteService"
    }
    private lateinit var mServiceBinder: ServiceBinder
    private lateinit var mRemoteServiceConn:RemoteServiceConnection

    override fun onCreate() {
        super.onCreate()

        mServiceBinder= ServiceBinder()

        if (mRemoteServiceConn == null) {
            mRemoteServiceConn = RemoteServiceConnection()
        }
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand")

        bindService(Intent(this, LocalService::class.java), mRemoteServiceConn,  Context.BIND_IMPORTANT)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mServiceBinder
    }

    /**
     * 通过AIDL实现进程间通讯
     */
    inner class ServiceBinder:IProcessService.Stub() {
        override fun getServiceName(): String {
            return "RemoteService"
        }
    }

    /**
     * 连接远程服务
     */
    inner class RemoteServiceConnection: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            try {
                /**
                 * 与远程服务通讯
                 */
                var process = IProcessService.Stub.asInterface(service)
                Log.d(TAG, "连接${process.serviceName}服务成功")
            } catch (exception: RemoteException) {
                Log.e(TAG, "连接服务出粗, ${exception.localizedMessage}")
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "远程服务挂掉了")
            startService(Intent(this@RemoteService, LocalService::class.java))
            bindService(Intent(this@RemoteService, LocalService::class.java), mRemoteServiceConn, Context.BIND_IMPORTANT)
        }
    }
}