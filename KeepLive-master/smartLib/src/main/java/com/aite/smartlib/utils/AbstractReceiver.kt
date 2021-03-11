package com.aite.smartlib.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 广播类封装
 */
open class AbstractReceiver : BroadcastReceiver() {

    private var mBlock: ((context: Context?, intent: Intent?) -> Unit)? = null

    fun onReceive(block: (context:Context?, intent: Intent?) -> Unit): AbstractReceiver {
        mBlock = block
        return this@AbstractReceiver
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        mBlock?.invoke(context, intent)
    }
}