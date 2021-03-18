package com.aite.keepLive

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import com.aite.keepLive.constants.AT_Constants
import com.aite.keepLive.constants.Constants
import com.aite.keepLive.utils.SystemUtil

object KeepLiveInit {
    fun initAppsKeepLive() {
        val timer = object : CountDownTimer(Long.MAX_VALUE, 10000) {
            override fun onFinish() {
                logger("keep live reInit")
                initAppsKeepLive()
            }

            override fun onTick(millisUntilFinished: Long) {
                checkGatewayConn()
//                checkSipServiceConn()
            }
        }
        timer.start()
    }

    private fun checkGatewayConn() {
        if (!SystemUtil.isProcessRun(KeepLiveApplication.mContext, Constants.Package.GATEWAY)) {
            logger("restart gateway")
            SystemUtil.restartGateway(KeepLiveApplication.mContext, 0)
        } else {
            logger("gateway running now." + Thread.currentThread().name)
        }
    }

    private fun checkSipServiceConn() {
        if (!SystemUtil.isServiceRun(KeepLiveApplication.mContext, Constants.Package.SIP_SERVICE)) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                logger("aitesip service restart now.")
                val service = Intent()
                service.component = ComponentName(
                        Constants.Config.SIP_PKG_NAME,
                        Constants.Package.SIP_SERVICE
                )
                KeepLiveApplication.mContext!!.startService(service)
            } else {
                KeepLiveApplication.mContext!!.sendBroadcast(Intent(AT_Constants.Action.ACTION_SCHEDULE_START_SERVER))
            }
        } else {
            logger("aitesip service run now." + Thread.currentThread().name)
        }
    }
}