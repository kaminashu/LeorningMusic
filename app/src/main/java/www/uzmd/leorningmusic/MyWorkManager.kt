package www.uzmd.leorningmusic

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorkManager(context: Context, val worker: WorkerParameters) : Worker(context, worker) {
    override fun doWork(): Result { //main sthet da ishlamaydi
        val page = worker.inputData.getInt(PAGE, 0)
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("$i $page")
        }
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        log("onStopped")
    }


    fun log(message: String) {
        Log.d("MY_SERVICE", "log: $message")
    }

    companion object {
        const val PAGE = "page"
        const val WORK_NAME = "ishchi"
        fun makeRecuest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorkManager>().setInputData(workDataOf(PAGE to page)).setConstraints(
                makerConstrains()
            )
                .build()
        }

        fun makerConstrains(): Constraints {
            return Constraints.Builder().setRequiresCharging(true).setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
        }
    }

}