package com.aite.smartlib.activity

import android.content.IntentFilter
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.aite.smartlib.utils.AbstractReceiver
import com.aite.smartlib.utils.Constants.Companion.ACTION_FINISH_ONE_PIXEL_ACTIVITY
import com.aite.smartlib.utils.registerReceiverSafely
import com.aite.smartlib.utils.unregisterReceiverSafely

class OnePixelActivity : AppCompatActivity() {
    private val mFinishReceiver by lazy {
        AbstractReceiver().onReceive { _, _ ->
            this@OnePixelActivity.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            setGravity(Gravity.START or Gravity.TOP)
            attributes = attributes.apply {
                x = 0
                y = 0
                width = 1
                height = 1
            }
        }
        registerReceiverSafely(mFinishReceiver, IntentFilter(ACTION_FINISH_ONE_PIXEL_ACTIVITY))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiverSafely(mFinishReceiver)
    }
}