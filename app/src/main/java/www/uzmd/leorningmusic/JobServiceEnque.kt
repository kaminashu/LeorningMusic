package www.uzmd.leorningmusic

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JobServiceEnque : JobService() {
    val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        scope.launch {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var workItem = params?.dequeueWork() // params dan work item qabul qilish
            while (workItem != null) { //smart cast null likdan qutqarildi hamda sikl auyantirildi
                val page = workItem.intent.getIntExtra(PAGE, 0) ?: 0 //page qabul qilib olindi
                    for (i in 0 until 5) {
                        delay(1000)
                        log("TIMER: $i $page")
                }
                params?.completeWork(workItem) //ushbu workitem dagi qiymat bajarildi degan buyruq bu
                workItem = params?.dequeueWork() // work item ni qayta qiymatlab qayta ishlatish
            }
        }
        }
        return true // dastur qanchadir vaqtdan kegin qayta ishlashi uchun
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true // uzilib qolgan paytida yana davom qilishga
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // scop destroy bolshi kerak
        log("onDestroy")
    }

    fun log(message: String) {
        Log.d("MyJobServiceEnque", "log: $message")
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

        fun getIntent(page: Int): Intent { // main patocga uzatiladigan intent
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }

        const val PAGE = "page" //key
        private fun message(str: String) {
            Log.d("MyForgraundService", "${str}")// log functuntion
        }
    }
}