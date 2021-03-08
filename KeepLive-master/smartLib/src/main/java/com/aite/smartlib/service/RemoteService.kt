package com.aite.smartlib.service

import com.aite.smartlib.manager.AudioManager
import com.aite.smartlib.manager.ForegroundManager
import com.aite.smartlib.manager.TimingManager

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
}