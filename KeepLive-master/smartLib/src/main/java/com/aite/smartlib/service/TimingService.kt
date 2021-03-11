package com.aite.smartlib.service

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.aite.smartlib.logger
import com.aite.smartlib.utils.startServiceSafely
import java.util.logging.Logger

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TimingService : JobService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY // 被杀后自动重启，保持启动状态，不保持Intent，重新调用onStartCommand，无新Intent则为空Intent—杀死重启后，不继续执行先前任务，能接受新任务
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        logger("2min到，唤醒保活服务，保持连接状态...")
        startServiceSafely(Intent(this, KeepLiveService::class.java))
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean = false
}