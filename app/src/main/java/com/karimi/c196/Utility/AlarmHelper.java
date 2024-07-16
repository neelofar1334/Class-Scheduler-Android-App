package com.karimi.c196.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
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
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
            long trigger = date.getTimeInMillis();

            Intent intent = new Intent(context, Alarm.class);
            intent.putExtra("title", title);
            intent.putExtra("text", "Reminder for " + details);

            PendingIntent sender = PendingIntent.getBroadcast(
                    context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Log.d("AlarmHelper", "Exact Alarm set for: " + title + " at " + sdf.format(date.getTime()) + " with ID " + notificationId);
                    Toast.makeText(context, "Exact Notification set for: " + title + " at " + sdf.format(date.getTime()), Toast.LENGTH_SHORT).show();
                } else {
                    //get user permission via system settings
                    Intent permitIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    context.startActivity(permitIntent);
                }
            } else {
                //if user permission is not needed
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger, sender);
                Log.d("AlarmHelper", "Exact Alarm set for older OS: " + title + " at " + sdf.format(date.getTime()) + " with ID " + notificationId);
                Toast.makeText(context, "Notification set for older OS: " + title + " at " + sdf.format(date.getTime()), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AlarmHelper", "Failed to parse date or set notification", e);
            Toast.makeText(context, "Failed to set notification. Check date.", Toast.LENGTH_SHORT).show();
        }
    }
}
