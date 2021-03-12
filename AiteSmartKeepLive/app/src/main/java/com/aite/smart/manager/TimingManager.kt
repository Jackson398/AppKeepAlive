package com.aite.smart.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import com.aite.smart.constants.Constants
import com.aite.smart.logger
import com.aite.smart.service.KeepLiveService
import com.aite.smart.service.TimingService

class TimingManager(private val mContext: Context) {
    private var mPendingIntent: PendingIntent? = null

    fun startJob() {
        logger("开启定时任务")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder = JobInfo.Builder(Constants.JOB_ID, ComponentName(mContext, TimingService::class.java))
            builder.setPeriodic(Constants.DEFAULT_WAKE_UP_INTERVAL)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setPeriodic(JobInfo.getMinPeriodMillis(), JobInfo.getMinFlexMillis())
            }
            builder.setPersisted(true)
            (mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler)?.schedule(builder.build())
        } else {
            val alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            alarmManager?.let {
                mPendingIntent = PendingIntent.getService(mContext, Constants.PENDING_INTENT_REQUEST_CODE, Intent(mContext, KeepLiveService::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
                it.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Constants.DEFAULT_WAKE_UP_INTERVAL, Constants.DEFAULT_WAKE_UP_INTERVAL, mPendingIntent)
            }
        }
    }

    fun stopJob() {
        logger("关闭定时任务")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val scheduler = mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.cancel(Constants.JOB_ID)
        } else {
            val am = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (mPendingIntent != null) {
                am.cancel(mPendingIntent)
            }
        }
    }
}