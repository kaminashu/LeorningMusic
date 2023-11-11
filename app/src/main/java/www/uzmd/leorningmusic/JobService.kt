package www.uzmd.leorningmusic

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JobService() : JobService() {
    val scop = CoroutineScope(Dispatchers.Main)
    override fun onStartJob(params: JobParameters?): Boolean {
        message("command")
        if (Build.VERSION.SDK_INT >= 34) {
            val page = params?.dequeueWork()?.extras?.getInt(PAGE) ?: 0
            scop.launch {
                for (i in 1 until 5) {
                    delay(1000)
                    message("Timer: $i  so`rov: $page")
                }

                jobFinished(params, true)
            }
        }
        return true
    }


    override fun onStopJob(params: JobParameters?): Boolean {
        message("onStopJob")
        return true
    }

    override fun onDestroy() {
        message("destroy zaryaddan uzildi")
        super.onDestroy()
        scop.cancel()

    }

    companion object {
        /*
        * servicelarni parallel ishlatmoqchi bo`lsang qamentga olingan funcsiyani qo`sh
        * aks holda uni tagidagini qo`sh ketma ket ishlatmoqchi bo`lsang albatta
        * */
        /*  fun getBundle(page: Int): PersistableBundle {
              return PersistableBundle().apply {
                  putInt(PAGE, page)
              }
          }*/

        fun getIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }

        const val PAGE = "page"
        private fun message(str: String) {
            Log.d("MyForgraundService", "${str}")
        }
    }
}