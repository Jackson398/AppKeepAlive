package com.aite.keepLive.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.Nullable;

public class RestartAppService extends IntentService {
    private static long stopDelayed = 500; // 关闭应用多久之后重启
    private Handler mHandler;
    private String mPackageName;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RestartAppService() {
        super("RestartAppService");
        mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        stopDelayed = intent.getLongExtra("Delay", 500);
        mPackageName = intent.getStringExtra("PackageName");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(mPackageName);
                startActivity(launchIntent);
            }
        }, stopDelayed);
    }
}
