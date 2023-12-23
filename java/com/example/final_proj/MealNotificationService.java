package com.example.final_proj;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MealNotificationService extends IntentService {

    private static final String CHANNEL_ID = "MealChannel";
    private static boolean channelCreated = false;

    public MealNotificationService() {
        super("MealNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MealNotificationService", "Handling notification intent");
        createNotificationChannel(); // Create notification channel if not created
        showNotification("New Item Added", "Check out our latest menu item!");
    }

    private void createNotificationChannel() {
        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !channelCreated) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Meal Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            channelCreated = true; // Set the flag to indicate that the channel is created
        }
    }

    private void showNotification(String title, String message) {
        Log.d("MealNotificationService", "Showing notification");
        createNotificationChannel(); // Ensure the channel is created

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Replace R.drawable.ic_notification with the resource ID of your small icon
        int smallIcon = R.drawable.cart; // Replace with your actual small icon resource ID

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(smallIcon) // Set a valid small icon
                        .setAutoCancel(true);

        // Use a unique notification ID, e.g., System.currentTimeMillis()
        long notificationId = System.currentTimeMillis();
        notificationManager.notify((int) notificationId, builder.build());
    }
}
