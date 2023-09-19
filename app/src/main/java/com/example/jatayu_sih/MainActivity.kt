package com.example.jatayu_sih

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jatayu_sih.apiModelCall.ResponseData
import com.example.jatayu_sih.apiModelCall.SessionResponse
import com.example.jatayu_sih.retrofit.RetrofitInstance
import com.example.jatayu_sih.retrofit.RetrofitInstance.apiInterface
import com.example.jatayu_sih.websocket.SocketHandler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import io.socket.client.Ack
import io.socket.emitter.Emitter
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: loginStatus
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    public var  lat: Double? =0.0
    public  var  long: Double? =0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.white, theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.white)
        }





        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val bottomNav= findViewById<BottomNavigationView>(R.id.bottom_navbar)


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.operation_fragment,
                R.id.notification_fragment,
                R.id.logs_fragment,
                R.id.profile_fragment
            )
        )

        bottomNav.setupWithNavController(navController)



        prefs = loginStatus(this@MainActivity)
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


        val call: Call<SessionResponse> = apiInterface.getCurrentSessionByTeamId(team)
        call.enqueue(object : Callback<SessionResponse> {
            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                Log.d("Retrofit Call HONA",response.body().toString())
                if (response.isSuccessful) {
                    val sessionResponse = response.body()

                    val sessionId = sessionResponse?.data?.sessions?.firstOrNull()?._id

                    // Handle the successful response here
                    Log.d("Retrofit Call HONA",sessionId.toString())
                    prefs.sessionsId=sessionId



                } else {
                    // Handle the error response here
                }
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                Log.d("retrofitError", t.message.toString())
            }
        })


        val apiInterface = RetrofitInstance.apiInterface

        val requestData = JSONObject()
        requestData.put("senderId", "${team}")
        requestData.put("receiverId", "${organisation}")
        requestData.put("teamId", "${team}")
        requestData.put("message", "Disaster Alert")
        requestData.put("status", "pending")
        requestData.put("estimatedAffectees", 0)

        val locationData = JSONObject()
        locationData.put("long", "${long.toString()}")
        locationData.put("lat", "${lat.toString()}")
        locationData.put("radius", 50)
        requestData.put("location", locationData)


        socket.emit("req-from-emp", requestData , Ack { args ->
            // Process the callback response here
            if (args.isNotEmpty()) {
                val response = args[0] // Assuming the response is the first argument
                println("Received response: $response")
                Log.d("SocketIOccc", "Received response: $response")

                val gson = Gson()
                val responseData = gson.fromJson(response.toString(), ResponseData::class.java)


                val createdAt = responseData.data.request.createdAt
                val teamId = responseData.data.request.teamId
                val estimatedAffectees = responseData.data.request.estimatedAffectees
                prefs.estimatedAffectees=estimatedAffectees.toString()

            }
        })

        socket.on("req-from-emp", onResponse)


    }

    private val onResponse = Emitter.Listener { args ->
        val response = args[0] as JSONObject
        val status = response.getString("status")
        Log.d("MAINRESPOSNEsocketResponse", status.toString())
        Toast.makeText(this,"this is onresponse", Toast.LENGTH_LONG).show()


        runOnUiThread {
            // Handle the response data here
            if (status == "success") {
                val data = response.getJSONObject("data")
                val request = data.getJSONObject("request")

                Log.d("socketResponse", request.toString())


            } else {
                val data = response.getJSONObject("data")
                val message = data.getString("message")
                Log.d("socketResponse", message.toString())
                // Extract values from the response JSON and perform actions as needed
            }
        }
    }



//private fun requestLocationUpdates() {
//    if (ActivityCompat.checkSelfPermission(this@MainAc, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        return
//    }
//    fusedLocationClient.lastLocation
//        .addOnSuccessListener { location ->
//            if (location != null) {
//                lat = location.latitude
//                long = location.longitude
//            }
//        }
//        .addOnFailureListener { e ->
//            Log.d("location_for-lat-Long","${e.message}")
//        }
//}
}