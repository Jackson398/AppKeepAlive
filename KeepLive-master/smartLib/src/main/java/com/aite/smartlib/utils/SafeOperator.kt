package com.aite.smartlib.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.*
import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception

fun Context.registerReceiverSafely(receiver: BroadcastReceiver, filter: IntentFilter) {
    catchExceptionByIgnore {
        this.registerReceiver(receiver, filter)
    }
}

fun Context.unregisterReceiverSafely(receiver: BroadcastReceiver) {
    catchExceptionByIgnore {
        this.unregisterReceiver(receiver)
    }
}

fun Context.bindServiceSafely(service: Intent, conn: ServiceConnection) {
    catchExceptionByIgnore {
        this.bindService(service, conn, Context.BIND_ABOVE_CLIENT)
    }
}

fun Context.unbindServiceSafely(conn: ServiceConnection) {
    catchExceptionByIgnore {
        this.unbindService(conn)
    }
}

fun Context.startServiceSafely(service: Intent) {
    catchExceptionByIgnore {
        this.startService(service)
    }
}

fun Context.stopServiceSafely(service: Intent) {
    catchExceptionByIgnore {
        this.stopService(service)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.startForegroundServiceSafely(service: Intent) {
    catchExceptionByIgnore {
        this.startForegroundService(service)
    }
}

fun Service.startForegroundSafely(id: Int, notification: Notification) {
    catchExceptionByIgnore {
        this.startForeground(id, notification)
    }
}

fun Service.stopForegroundSafely(removeNotification: Boolean) {
    catchExceptionByIgnore {
        this.stopForeground(removeNotification)
    }
}

fun NotificationManager.cancelSafely(id: Int) {
    catchExceptionByIgnore {
        this.cancel(id)
    }
}

fun catchExceptionByIgnore(block:() -> Unit) {
    try {
        block()
    } catch (ignore: Exception) {
        // ignore
    }
}