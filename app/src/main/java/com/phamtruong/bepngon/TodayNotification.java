package com.phamtruong.bepngon;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.phamtruong.bepngon.ui.main.MainActivity;
import com.phamtruong.bepngon.util.Constant;

public class TodayNotification {
    private static final int TODAY_NOTIFY_ID = 20122022;

    public static void showNotify(Context context, String today, String message, String from) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Intent intent = new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.NOTI_DATA, from);
        int flag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            flag = PendingIntent.FLAG_UPDATE_CURRENT;
        }

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, flag);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constant.CHANNEL_NOTIFY_ID)
                .setSmallIcon(R.drawable.logo_google)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(today)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // notificationId is a unique int for each notification that you must define
        Log.e("dddd", "showNotify: ");
        notificationManager.notify(TODAY_NOTIFY_ID, builder.build());
    }
}
