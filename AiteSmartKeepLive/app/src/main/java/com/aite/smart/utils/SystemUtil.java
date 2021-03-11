package com.aite.smart.utils;

import android.content.Context;
import android.content.Intent;

import com.aite.smart.service.RestartAppService;

public class SystemUtil {
    public static void restartGateway(Context context, long delay) {
        Intent intent = new Intent(context, RestartAppService.class);
        intent.putExtra("PackageName", "at.smarthome.zh.gateway");
        intent.putExtra("Delay", delay);
        context.startService(intent);
    }
}
