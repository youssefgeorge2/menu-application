package com.example.final_proj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyBroadcastReceiver", "Broadcast received"); // Additional log message

        String action = intent.getAction();

        Log.d(TAG, "Received broadcast with action: " + action);

        if ("com.example.final_proj.LOGIN_SUCCESSFUL".equals(action)) {
            String username = intent.getStringExtra("username");
            Log.d(TAG, "Login Successful for user: " + username);
            Toast.makeText(context, "Login Successful for user: " + username, Toast.LENGTH_SHORT).show();
        } else if ("com.example.final_proj.REGISTRATION_SUCCESSFUL".equals(action)) {
            String username = intent.getStringExtra("username");
            Log.d(TAG, "Registration Successful for user: " + username);
            Toast.makeText(context, "Registration Successful for user: " + username, Toast.LENGTH_SHORT).show();
        }
        // Add more conditions for other broadcast actions if needed
    }
}
