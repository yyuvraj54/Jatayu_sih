package com.example.jatayu_sih

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}