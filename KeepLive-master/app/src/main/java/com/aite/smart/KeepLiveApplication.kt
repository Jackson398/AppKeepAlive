package com.aite.smart

import android.app.Application
import com.aite.smartlib.init

class KeepLiveApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        if (init(this)) {
            return
        }
        // 初始化其他操作
    }
}