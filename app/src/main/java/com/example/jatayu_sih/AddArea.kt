package com.example.jatayu_sih

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.core.app.ActivityCompat
import com.example.jatayu_sih.apiModelCall.ResponseData
import com.example.jatayu_sih.websocket.SocketHandler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import io.socket.client.Ack
import org.json.JSONObject

class AddArea : AppCompatActivity() {
    private lateinit var etLocationLatitude:TextView
    private lateinit var etLocationLongitude:TextView
    private lateinit var spinner:Spinner
    private lateinit var btnSend:Button
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var prefs: loginStatus


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_area)

        etLocationLatitude=findViewById(R.id.etLocationLatitude)
        etLocationLongitude=findViewById(R.id.etLocationLongitude)
        spinner=findViewById(R.id.etSeverity1)
        btnSend=findViewById(R.id.btnSendArea)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.white, theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.white)
        }

        val spinnerSeverity=arrayOf("0","1","2")
        val arrayAdp= ArrayAdapter(this@AddArea,android.R.layout.simple_spinner_dropdown_item,spinnerSeverity)
        spinner.adapter=arrayAdp

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()

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

        btnSend.setOnClickListener {
            val latitude = etLocationLatitude.text.toString()
            val longitude = etLocationLongitude.text.toString()

            val selectedSeverity = spinner.selectedItem.toString()


            val requestData = JSONObject()
            requestData.put("senderId", "${team}")
            requestData.put("receiverId", "${organisation}")
            requestData.put("teamId", "${team}")
            requestData.put("message", "Disaster Alert")
            requestData.put("status", "pending")
            requestData.put("estimatedAffectees", 0)

            requestData.put("senderId", "${team}")
            requestData.put("receiverId", "${organisation}")
            requestData.put("teamId", "${team}")
            requestData.put("message", "Disaster Alert")
            requestData.put("status", "pending")
            requestData.put("estimatedAffectees", 0)

            val locationData = JSONObject()
            locationData.put("latitude", latitude)
            locationData.put("longitude", longitude)
            locationData.put("severityLevel", selectedSeverity)
            requestData.put("updateData", locationData)

            // Send requestData to the server via WebSocket
            socket.emit("req-from-emp", requestData, Ack{ args->
                Log.d(Constraints.TAG,"Empty")
                if (args.isNotEmpty()) {
                    val response = args[0] // Assuming the response is the first argument
                    println("Received response: $response")
                    Log.d("SocketIO", "Received response: $response")

                    val gson = Gson()
                    val responseData = gson.fromJson(response.toString(), ResponseData::class.java)
                    Toast.makeText(this,"Request sent",Toast.LENGTH_SHORT).show()
                    Log.d(Constraints.TAG,"Request sent")

                }

            })

        }
    }

    private fun getCurrentLocation(){
        if(checkPermission())
        {
            if(isLocationEnabled())
            {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) {task->
                    val location: Location?=task.result
                    if(location==null)
                    {
                        Toast.makeText(this,"Null", Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        etLocationLatitude.text=""+location.latitude
                        etLocationLongitude.text=""+location.longitude
                    }
                }
            }
            else
            {
                Toast.makeText(this,"Turn on location", Toast.LENGTH_SHORT).show()

            }
        }
        else
        {
            requestPermission()
        }
    }

    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }

    private fun checkPermission():Boolean
    {
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED  &&
            ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED )
        {
            return true
        }
        return false
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(applicationContext,"Granted",Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else{
                Toast.makeText(applicationContext,"Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }
}