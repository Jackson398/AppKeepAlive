package com.aite.smartlib.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import com.aite.smartlib.logger
import com.aite.smartlib.service.KeepLiveService
import com.aite.smartlib.service.TimingService
import com.aite.smartlib.utils.Constants.Companion.DEFAULT_WAKE_UP_INTERVAL
import com.aite.smartlib.utils.Constants.Companion.JOB_ID
import com.aite.smartlib.utils.Constants.Companion.PENDING_INTENT_REQUEST_CODE

class TimingManager(private val mContext: Context) {
    private var mPendingIntent: PendingIntent? = null

    fun startJob() {
        logger("开启定时任务")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder = JobInfo.Builder(JOB_ID, ComponentName(mContext, TimingService::class.java))
            builder.setPeriodic(DEFAULT_WAKE_UP_INTERVAL)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setPeriodic(JobInfo.getMinPeriodMillis(), JobInfo.getMinFlexMillis())
            }
            builder.setPersisted(true)
            (mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler)?.schedule(builder.build())
        } else {
            val am = mContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            am?.let {
                mPendingIntent = PendingIntent.getService(mContext, PENDING_INTENT_REQUEST_CODE,
                        Intent(mContext, KeepLiveService::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
                it.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + DEFAULT_WAKE_UP_INTERVAL, DEFAULT_WAKE_UP_INTERVAL,
                        mPendingIntent)
            }
        }
    }

    fun stopJob() {
        logger("关闭定时任务")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val scheduler = mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.cancel(JOB_ID)
        } else {
            val am = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (mPendingIntent != null) {
                am.cancel(mPendingIntent)
            }
        }
    }
}