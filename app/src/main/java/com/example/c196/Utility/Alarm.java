package com.example.c196.Utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.c196.R;

public class Alarm extends BroadcastReceiver {
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static int notificationID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("text");
        String title = intent.getStringExtra("title");
        Log.d("Alarm", "Alarm Received: " + title + " - " + message);
        Toast.makeText(context, title + ": " + message, Toast.LENGTH_LONG).show();

        //create notification channel
        createNotificationChannel(context);

        //prepare and issue notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_school_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, builder.build());
    }

    //notification channel
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Course and Assessment Notifications";
            String description = "Channel for Course and Assessment Start and End Date Reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}