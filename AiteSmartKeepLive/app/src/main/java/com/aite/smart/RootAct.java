package com.aite.smart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.aite.smart.UtilFunctionKt.logger;

public class RootAct extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) || Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            logger("======start server======");
            KeepLiveInit.INSTANCE.startKeepLiveService();
        }
    }
}
