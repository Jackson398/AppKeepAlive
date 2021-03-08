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

class LocalService : Service() {
    companion object {
        const val TAG = "LocalService"
    }

    private lateinit var mLocalBinder: LocalBinder
    private lateinit var mLocalServiceConn: LocalServiceConnection

    override fun onCreate() {
        super.onCreate()
        mLocalBinder = LocalBinder()
        if (mLocalServiceConn == null) {
            mLocalServiceConn = LocalServiceConnection()
        }
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mLocalBinder
    }

    inner class LocalBinder : IProcessService.Stub() {
        override fun getServiceName(): String {
            return "LocalService"
        }
    }

    inner class LocalServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            try {
                var process = IProcessService.Stub.asInterface(service)
                Log.d(TAG, "连接${process.serviceName}服务成功")
            } catch (exception: RemoteException) {
                Log.e(TAG, "连接服务出错，${exception.localizedMessage}")
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.w(TAG, "远程服务挂掉了")
            startService(Intent(this@LocalService, RemoteService::class.java))
            bindService(Intent(this@LocalService, RemoteService::class.java), mLocalServiceConn, Context.BIND_IMPORTANT)
        }
    }
}