package com.aite.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.aite.smart.service.LocalService;
import com.aite.smart.service.RemoteService;

/**
 * Android 双进程守护，应用保活
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
    }
}