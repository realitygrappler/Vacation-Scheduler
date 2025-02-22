package com.example.d308_mobile_application_development_android.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.d308_mobile_application_development_android.R;

public class CReceiver extends BroadcastReceiver {

    static int notificationID = 1;  // Notification ID
    private static final String channel_id = "vacation_channel";  // Channel ID
    private static final String excursion_channel_id = "excursion_channel"; // Unique channel ID


    @Override
    public void onReceive(Context context, Intent intent) {
        String channel = intent.hasExtra("excursion_channel") ? excursion_channel_id : channel_id;

        // Debugging - log the received notification key
        Log.d("CReceiver", "Received notification key: " + intent.getStringExtra("key"));

        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel);

        Notification notification = new NotificationCompat.Builder(context, channel)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("NotificationTest")
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);

        // Debugging - log notification ID and channel used
        Log.d("CReceiver", "Notification sent with ID: " + notificationID + " to channel: " + channel);
    }


    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description;

        if (CHANNEL_ID.equals(excursion_channel_id)) {
            name = context.getResources().getString(R.string.excursion_channel_name);
            description = context.getString(R.string.excursion_channel_description);
        } else {
            name = context.getResources().getString(R.string.channel_name);
            description = context.getString(R.string.channel_description);
        }

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Debugging - log channel creation
        Log.d("CReceiver", "Notification channel created: " + CHANNEL_ID);
    }
}
