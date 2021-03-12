package com.aite.smart.service

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import com.aite.smart.KeepLiveInit
import com.aite.smart.logger
import com.aite.smart.startServiceSafely
import com.aite.smart.stopServiceSafely
import com.aite.smart.utils.SystemUtil

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TimingService : JobService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        logger("时间到，重启网关，重启保活服务...")
        SystemUtil.restartGateway(this, 0) // 网关被强制kill，需要网关重启
        Handler().postDelayed({ restartKeepLiveService() }, 2000)
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean = false

    private fun restartKeepLiveService() {
        stopServiceSafely(Intent(this, KeepLiveService::class.java))
        startServiceSafely(Intent(this, KeepLiveService::class.java))
    }
}