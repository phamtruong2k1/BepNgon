package com.phamtruong.bepngon

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.util.FlagUtil


class NotificationReceiver : Service() {


    val CHANNEL_ID = "ForegroundServiceChannel"
    override fun onCreate() {
        super.onCreate()
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotification(this, "Dc ra la", Constant.NOTI_1)
        val input = intent.getStringExtra("inputExtra")
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, FlagUtil.getFlag(0)
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Ok la")
            .setSmallIcon(R.drawable.logo_splash)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        startForeground(1, notification)
        //do heavy work on a background thread
        //stopSelf();
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(context: Context?, content: String, type: String) {
        TodayNotification.showNotify(
            context,
            context?.resources?.getString(R.string.app_name),
            content,
            type
        )
    }

}