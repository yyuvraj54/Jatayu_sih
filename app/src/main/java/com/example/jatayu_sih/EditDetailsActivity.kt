package com.example.jatayu_sih

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

class EditDetailsActivity : AppCompatActivity() {

    private lateinit var etSurviveurs:EditText
    private lateinit var etInjured:EditText
    private lateinit var etEstimatedAfectees:EditText
    private lateinit var etCasualities:EditText
    private lateinit var spinner:Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_details)

        etSurviveurs=findViewById(R.id.etSurviversDataFill)
        etInjured=findViewById(R.id.etInjuredDataFill)
        etEstimatedAfectees=findViewById(R.id.etEstimatedAfffectiesDataFill)
        etCasualities=findViewById(R.id.etCasualitiesDataFill)
        val spinnerSeverity=arrayOf("No Severity","0","1","2")

        val arrayAdp= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spinnerSeverity)
        spinner.adapter=arrayAdp

    }
}