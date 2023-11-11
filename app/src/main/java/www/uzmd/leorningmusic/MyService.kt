package www.uzmd.leorningmusic

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    var scop = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        super.onCreate()
        message("start")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        message("command")
        scop.launch {
            for (i in 1 until 100) {
                delay(1000)
                message("$i")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        message("Destroy")
        scop.cancel()
    }

    fun message(str: String) {
        Log.d("MyService", "${str}")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyService::class.java)
        }
    }
}