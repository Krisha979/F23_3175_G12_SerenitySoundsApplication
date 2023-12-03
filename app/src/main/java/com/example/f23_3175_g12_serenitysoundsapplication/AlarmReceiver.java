package com.example.f23_3175_g12_serenitysoundsapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int STOP_DELAY = 10000;
    private Handler handler = new Handler();
    MediaPlayer mp;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "ReminderChannel";
    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the alarm event here, e.g., show a notification or update UI
        mp = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mp.setLooping(false);
        mp.start();
        String title = intent.getStringExtra("title");
        Toast.makeText(context, "Reminder: " + title, Toast.LENGTH_SHORT).show();

        // Schedule a task to stop the MediaPlayer after a specified delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopMediaPlayer();
            }
        }, STOP_DELAY);
        // Call showNotification to display the notification
        showNotification(context, title);
    }
    private void stopMediaPlayer() {
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
    private void showNotification(Context context, String message) {
        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reminder Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create an intent for launching the app when the notification is clicked
        Intent launchIntent = new Intent(context, MainActivity.class);

        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, PendingIntent.FLAG_IMMUTABLE);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notification_important_24)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
