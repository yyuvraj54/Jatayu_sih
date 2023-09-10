package com.example.jatayu_sih

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(onBoardingFinished()){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun onBoardingFinished():Boolean{
        val sharedPref =getSharedPreferences ("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)

    }
}