package com.example.jatayu_sih

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.jatayu_sih.apiModelCall.ResponseData
import com.example.jatayu_sih.websocket.SocketHandler
import com.google.gson.Gson
import io.socket.client.Ack
import org.json.JSONObject

class EditDetailsActivity : AppCompatActivity() {
    private lateinit var etSurviveurs: EditText
    private lateinit var etInjured: EditText
    private lateinit var etEstimatedAfectees: EditText
    private lateinit var etCasualities: EditText
    private lateinit var spinner: Spinner
    private lateinit var prefs: loginStatus
    private lateinit var btnAdd:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_details)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.white, theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.white)
        }
        etSurviveurs=findViewById(R.id.etSurviversDataFill)
        etInjured=findViewById(R.id.etInjuredDataFill)
        etCasualities=findViewById(R.id.etCasualitiesDataFill)
        etEstimatedAfectees=findViewById(R.id.etEstimatedAfffectiesDataFill)
        spinner=findViewById(R.id.etSeverityLevelDataFill)
        btnAdd=findViewById(R.id.btnAdd)

        val spinnerSeverity=arrayOf("0","1","2")
        val arrayAdp= ArrayAdapter(this@EditDetailsActivity,android.R.layout.simple_spinner_dropdown_item,spinnerSeverity)
        spinner.adapter=arrayAdp


        prefs = loginStatus(this)
        //val isLoggedIn = prefs.isLoggedIn
        val userId = prefs.userId
        val username = prefs.username.toString()
        val password = prefs.password.toString()
        val token = prefs.token.toString()
        val userRole = prefs.userRole.toString()
        val team = prefs.team.toString()
        val organisation = prefs.organisation.toString()

        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        val socket = SocketHandler.getSocket()

        btnAdd.setOnClickListener {





            val survivor = etSurviveurs.text.toString()
            val injured = etInjured.text.toString()
            val casualties = etCasualities.text.toString()
            val estemiated= etEstimatedAfectees.text.toString()

            val selectedSeverity = spinner.selectedItem.toString()

            prefs.survivors=survivor
            prefs.injured=injured
            prefs.casualties=casualties
            prefs.severity=selectedSeverity
            prefs.estimatedAffectees=estemiated



            val requestData = JSONObject()
            requestData.put("senderId", "${team}")
            requestData.put("receiverId", "${organisation}")
            requestData.put("teamId", "${team}")
            requestData.put("message", "Disaster Alert")
            requestData.put("status", "pending")
            requestData.put("estimatedAffectees", 0)


            val updateData = JSONObject()
            updateData.put("survivors", survivor)
            updateData.put("injured", injured)
            updateData.put("casualties", casualties)
            updateData.put("severityLevel", selectedSeverity)
            updateData.put("estimated affectees",estemiated)
            requestData.put("updateData", updateData)

            // Send requestData to the server via WebSocket
            socket.emit("req-from-emp", requestData, Ack{ args->
                Log.d(TAG,"Empty")
                if (args.isNotEmpty()) {
                    val response = args[0] // Assuming the response is the first argument
                    println("Received response: $response")
                    Log.d("SocketIO", "Received response: $response")

                    val gson = Gson()
                    val responseData = gson.fromJson(response.toString(),ResponseData::class.java)


//                    Toast.makeText(this,"Request sent",Toast.LENGTH_SHORT).show()
                    Log.d(TAG,"Request sent")

                }

            })
            
        }



    }
}