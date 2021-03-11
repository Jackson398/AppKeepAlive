package com.aite.smart;

import android.app.Application;
import android.content.Context;

public class KeepLiveApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        if (KeepLiveInit.INSTANCE.init(this)) {
            KeepLiveInit.INSTANCE.startKeepLiveService();
        }
        // 初始化其他操作
    }

    public static Context getContext() {
        return appContext;
    }
}
