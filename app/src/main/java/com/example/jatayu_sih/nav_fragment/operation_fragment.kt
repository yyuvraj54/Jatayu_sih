package com.example.jatayu_sih.nav_fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.example.jatayu_sih.R
import com.example.jatayu_sih.SessionViewActivity
import com.example.jatayu_sih.loginStatus
import com.example.jatayu_sih.websocket.webSocketListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import io.socket.client.IO
import android.app.Activity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jatayu_sih.apiModelCall.ResponseData
import com.example.jatayu_sih.apiModelCall.SessionResponse
import com.example.jatayu_sih.retrofit.RetrofitInstance
import com.example.jatayu_sih.websocket.SocketHandler
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import io.socket.client.Ack
import io.socket.client.Socket

import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class operation_fragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    public var  lat: Double? =0.0
    public  var  long: Double? =0.0
    private lateinit var prefs: loginStatus


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment




        val view=inflater.inflate(R.layout.fragment_operation_fragment, container, false)

        val notifybtn= view.findViewById<Button>(R.id.notifyOrgbtn)


//
//        val supportMapFragment= childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
//        supportMapFragment.getMapAsync { googleMap: GoogleMap -> }





        prefs = loginStatus(requireContext())
        //val isLoggedIn = prefs.isLoggedIn
        //val userId = prefs.userId
        val username = prefs.username.toString()
        val password = prefs.password.toString()
        val token = prefs.token.toString()
        val userRole = prefs.userRole.toString()
        val team = prefs.team.toString()
        val organisation = prefs.organisation.toString()


        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        val socket = SocketHandler.getSocket()

        notifybtn.setOnClickListener {



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
                        Log.d("SocketIO", "Received response: $response")

                        val gson = Gson()
                        val responseData = gson.fromJson(response.toString(), ResponseData::class.java)


                        val createdAt = responseData.data.request.createdAt
                        val teamId = responseData.data.request.teamId
                        Log.d("SocketIO", "createdAt: $createdAt")
                        Log.d("SocketIO", "teamId: $teamId")
                    }
                })

            socket.on("req-from-emp", onResponse)


            }
















//        open sessions card view
        val textview=view.findViewById<TextView>(R.id.textView3)
        textview.setOnClickListener {
            val intent= Intent(activity,SessionViewActivity::class.java)
            startActivity(intent)
        }




        return  view
    }

//
     private val onResponse = Emitter.Listener { args ->
        val response = args[0] as JSONObject
        val status = response.getString("status")
        Log.d("MAINRESPOSNEsocketResponse", status.toString())
        Toast.makeText(requireContext(),"this is onresponse",Toast.LENGTH_LONG).show()


        activity?.runOnUiThread {
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
//fun onMapReady(googleMap: GoogleMap) {
//    // Add a marker to the map
//    val location = lat?.let { long?.let { it1 -> LatLng(it, it1) } } // Replace with your desired coordinates
//    location?.let { MarkerOptions().position(it).title("Marker Title") }
//        ?.let { googleMap.addMarker(it) }
//    location?.let { CameraUpdateFactory.newLatLng(it) }?.let { googleMap.moveCamera(it) }
//}

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                }
            }
            .addOnFailureListener { e ->
                Log.d("location_for-lat-Long","${e.message}")
            }
    }


}