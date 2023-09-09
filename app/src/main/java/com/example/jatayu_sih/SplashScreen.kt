package com.example.jatayu_sih

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jatayu_sih.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.white, theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.white)
        }
        val thread = Thread {
            try {
                Thread.sleep(4000)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                val intent = Intent(this@SplashScreen, Login::class.java)
                startActivity(intent)
            }
        }
        thread.start()
    }
}