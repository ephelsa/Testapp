package com.example.leonardo.testapplication.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.leonardo.testapplication.ContainerActivity;
import com.example.leonardo.testapplication.R;

/**
 * Created by leonardo on 24/01/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "InfoReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, ContainerActivity.class);

        String time = String.valueOf(intent.getExtras().getInt("time") + "s");
        String data = intent.getExtras().getString("data");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ContainerActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle("Notificación hecha a los " + time)
                .setContentText(data)
                .setTicker("Mensaje de alerta!")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setVibrate(new long[] {1000, 1000, 1000})
                .setCategory(Notification.CATEGORY_EVENT)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);
    }
}
