package com.example.jatayu_sih.nav_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.jatayu_sih.R
import com.example.jatayu_sih.SessionViewActivity
import com.example.jatayu_sih.websocket.webSocketListener
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class operation_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_operation_fragment, container, false)




        val supportMapFragment= childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap: GoogleMap -> }

        val client=OkHttpClient()
        val apiKey="Asy8kbmka90j8fwkMVhqhxruIXPHgdNL8egnypmJ"
        val channelId=1
        val request :Request=Request
            .Builder()
            .url("wss://free.blr2.piesocket.com/v3/$channelId?api_key=$apiKey&notify_self=1")
            .build()


        val listener = webSocketListener()
        val ws :WebSocket = client.newWebSocket(request,listener)

        //open sessions card view
        val cardView=view.findViewById<CardView>(R.id.cvSessions)
        cardView.setOnClickListener {
            val intent= Intent(activity,SessionViewActivity::class.java)
            startActivity(intent)
        }
        return  view
    }

}