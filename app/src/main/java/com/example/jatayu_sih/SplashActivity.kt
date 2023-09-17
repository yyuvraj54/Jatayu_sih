package com.example.jatayu_sih

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.example.jatayu_sih.databinding.ActivityLoginBinding
import com.example.jatayu_sih.databinding.ActivitySplashScreenBinding

class SplashActivity : AppCompatActivity() {

    private val delayMillis: Long = 2000 // 2 seconds
   private lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.white, theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.white)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            binding.splashImage.setImageResource(R.drawable.img_2)
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashActivity, Login::class.java)
                startActivity(intent)
                finish()
            },delayMillis)
        },delayMillis)
    }
}