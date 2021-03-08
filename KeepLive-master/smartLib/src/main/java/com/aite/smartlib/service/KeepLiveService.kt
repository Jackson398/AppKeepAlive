package com.aite.smartlib.service

import android.content.Intent
import com.aite.smartlib.manager.ForegroundManager
import com.aite.smartlib.manager.ScreenManager

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
}