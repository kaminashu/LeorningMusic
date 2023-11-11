package www.uzmd.leorningmusic

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import www.uzmd.leorningmusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var page = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.service.setOnClickListener {
            startService(MyService.newIntent(this))
        }
        binding.FORGRAUNDservice.setOnClickListener {
            ContextCompat.startForegroundService(this, MyForgraundService.newIntent(this))
        }
        binding.jobService.setOnClickListener {
            myJobServiceEnque()
        }
        binding.workManager.setOnClickListener {
            myWorkService()
        }

    }


    private fun myWorkService() {
    val workManager= WorkManager.getInstance(applicationContext)
        workManager.enqueueUniqueWork(MyWorkManager.WORK_NAME,ExistingWorkPolicy.APPEND,MyWorkManager.makeRecuest(page++))
    }

    /*
        private fun myNatificationFunc() {
            val notifayManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notifayManager.createNotificationChannel(notificationChannel)
                Log.e("myTag", "myNatificationFunc: True")
            }

            val notCompact = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Ortiqboy")
                .setContentText("yuklanmoqda")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            notifayManager.notify(1, notCompact)

        }*/
    private fun myJobService() {
        val componentName =
            ComponentName(this, JobService::class.java) //companent name info uchun kerak
        val jobInfo = JobInfo.Builder(111, componentName)// strukturasi yasaladi
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) //zaryatga qoyilganda
            .setRequiresCharging(true) //wifi yoniq bo`lsa
            //    .setExtras(JobService.getBundle(++page)) // servicelarni parallel ishga tushurish uchun yoqiladi
            .build() //ishlaydi
        val jobScheduler =
            getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler // jobshedulerga cast qilindi
        //  jobScheduler.schedule(jobInfo) //parallel ishga tushgan vaqtida  shunday foydalan axmoq :)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            jobScheduler.enqueue(jobInfo, JobWorkItem(JobService.getIntent(++page)))
        }
    }

    private fun myJobServiceEnque() {
        val componentName = ComponentName(this, JobServiceEnque::class.java)
        val jobInfo = JobInfo.Builder(222, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED).setRequiresCharging(true)
            .build()
        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            jobScheduler.enqueue(jobInfo, JobWorkItem(JobServiceEnque.getIntent(++page)))
        }
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"

    }
}