package com.aite.smartlib.manager

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import com.aite.smartlib.R
import com.aite.smartlib.logger
import com.aite.smartlib.utils.UtilFunction
import kotlin.concurrent.thread

internal class AudioManager(private val context: Context) {
    private val mHandler by lazy {
        Handler()
    }

    private val mMediaPlayer by lazy {
        MediaPlayer.create(context, R.raw.silent)
    }

    init {
        mMediaPlayer.isLooping = true
    }

    fun startMusic() {
        thread {
            mMediaPlayer.start()
        }
        startTask()
    }

    fun stopMusic() {
        mMediaPlayer.stop()
        stopTask()
    }

    private fun startTask() {
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                logger("音频是否在播放：${mMediaPlayer.isPlaying}")
                logger("processName: ${UtilFunction.getCurrentProcessName(context)}")
                mHandler.postDelayed(this, 30 * 1000)
            }

        }, 30 * 1000)
    }

    private fun stopTask() {
        mHandler.removeCallbacksAndMessages(null)
    }
}