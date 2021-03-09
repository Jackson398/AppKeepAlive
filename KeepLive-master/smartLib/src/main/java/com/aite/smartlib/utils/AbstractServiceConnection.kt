package com.aite.smartlib.utils

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class AbstractServiceConnection : ServiceConnection{
    private var mConnectedBlock: ((name: ComponentName?, service: IBinder?) -> Unit)? = null

    private var mDisconnectedBlock: ((name: ComponentName?) -> Unit)? = null

    fun onServiceConnected(connectedBlock: (name: ComponentName?, service: IBinder?) -> Unit): AbstractServiceConnection {
        mConnectedBlock = connectedBlock
        return this@AbstractServiceConnection
    }

    fun onServiceDisConnected(disconnectedBlock: (name: ComponentName?) -> Unit): AbstractServiceConnection {
        mDisconnectedBlock = disconnectedBlock
        return this@AbstractServiceConnection
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        mConnectedBlock?.invoke(name, service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        mDisconnectedBlock?.invoke(name)
    }
}