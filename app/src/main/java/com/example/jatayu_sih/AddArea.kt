package com.example.jatayu_sih

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class AddArea : AppCompatActivity() {
    private lateinit var etLocationLatitude:EditText
    private lateinit var etLocationLongitude:EditText
    private lateinit var etSeverity:EditText
    private lateinit var btnSend:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_area)

        etLocationLatitude=findViewById(R.id.etLocationLatitude)
        etLocationLongitude=findViewById(R.id.etLocationLongitude)
        etSeverity=findViewById(R.id.etSeverity1)
        btnSend=findViewById(R.id.btnSendArea)
    }
}