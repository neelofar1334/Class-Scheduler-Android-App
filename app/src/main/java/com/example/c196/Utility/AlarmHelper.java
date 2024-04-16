package com.example.c196.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AlarmHelper {

    public static void setNotification(Context context, String dateStr, String title, String details, int notificationId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());

        try {
            Calendar date = Calendar.getInstance();
            date.setTime(sdf.parse(dateStr));
            long trigger = date.getTimeInMillis();

            Intent intent = new Intent(context, Alarm.class);
            intent.putExtra("title", title);
            intent.putExtra("text", "Reminder for " + details);

            PendingIntent sender = PendingIntent.getBroadcast(
                    context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            Log.d("AlarmHelper", "Alarm set for: " + title + " at " + sdf.format(date.getTime()) + " with ID " + notificationId);
            Toast.makeText(context, "Notification set for: " + title + " at " + sdf.format(date.getTime()), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AlarmHelper", "Failed to parse date or set notification", e);
            Toast.makeText(context, "Failed to set notification. Check date.", Toast.LENGTH_SHORT).show();
        }
    }
}
