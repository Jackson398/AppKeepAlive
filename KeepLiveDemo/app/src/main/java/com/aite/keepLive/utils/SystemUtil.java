package com.aite.keepLive.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.aite.keepLive.service.RestartAppService;

import java.util.List;

public class SystemUtil {
    public static boolean isServiceRun(Context context, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(50);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static boolean isProcessRun(Context context, String processName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(50);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).process;
            if (mName.equals(processName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static void restartGateway(Context context, long delay) {
        Intent intent = new Intent(context, RestartAppService.class);
        intent.putExtra("PackageName", "at.smarthome.zh.gateway");
        intent.putExtra("Delay", delay);
        context.startService(intent);
    }
}
