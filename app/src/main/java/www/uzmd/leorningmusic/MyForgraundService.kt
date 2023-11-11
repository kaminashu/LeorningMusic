package www.uzmd.leorningmusic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyForgraundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    var scop = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        super.onCreate()
        notificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        message("start")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        message("command")
        scop.launch {
            for (i in 1 until 20) {
                delay(1000)
                message("$i")
            }
            stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        message("Destroy")
        scop.cancel()
    }

    fun message(str: String) {
        Log.d("MyForgraundService", "${str}")
    }

    private fun notificationChannel() {
        val notifayManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "hello world"
            }
            notifayManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(): Notification {
        val intent=Intent(this,MainActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Ortiqboy")
            .setContentText("yuklanmoqda")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyForgraundService::class.java)
        }

        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"
    }
}