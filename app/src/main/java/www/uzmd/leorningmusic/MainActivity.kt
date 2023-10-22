package www.uzmd.leorningmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import www.uzmd.leorningmusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.service.setOnClickListener{
            startService(MyService.newIntent(this))
        }
    }
}