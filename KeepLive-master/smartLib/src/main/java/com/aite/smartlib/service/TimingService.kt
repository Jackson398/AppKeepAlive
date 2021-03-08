package com.aite.smartlib.service

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.aite.smartlib.utils.startServiceSafely

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TimingService : JobService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        startServiceSafely(Intent(this, KeepLiveService::class.java))
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean = false
}