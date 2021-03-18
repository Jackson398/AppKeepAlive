package com.aite.keepLive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            int upgradeVersion = Integer.parseInt("1.0.1");
            Log.d(TAG, "upgradeVersion=" + upgradeVersion);
        } catch (Exception exception) {
            Log.e(TAG, "exception = " + exception.getMessage());
        }
    }
}