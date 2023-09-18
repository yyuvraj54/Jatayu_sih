package com.example.jatayu_sih.nav_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.R
import com.example.jatayu_sih.adapter.Myadapter
import com.example.jatayu_sih.adapter.NotificationAdapter
import com.example.jatayu_sih.apiModelCall.NotificationResponse
import com.example.jatayu_sih.dataClass.Notification_data
import com.example.jatayu_sih.dataClass.Sessions
import com.example.jatayu_sih.loginStatus
import com.example.jatayu_sih.retrofit.RetrofitInstance
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class notification_fragment : Fragment() {


    private lateinit var noti_RecyclerView: RecyclerView
    private lateinit var state : ArrayList<String>
    private lateinit var createdAt : ArrayList<String>
    private lateinit var meassage: ArrayList<String>
    private lateinit var prefs: loginStatus
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_notification_fragment, container, false)

        prefs = loginStatus(requireContext())
        val team = prefs.team.toString()


        RetrofitInstance.apiInterface.getAllRequestsByTeamId(team)?.enqueue(object: Callback<NotificationResponse?>{
            override fun onResponse(call: Call<NotificationResponse?>, response: Response<NotificationResponse?>) {
                if (response.isSuccessful) {
                    val notificationResponse = response.body()
                    if (notificationResponse != null) {
                        val status = notificationResponse.status
                        val data = notificationResponse.data
                        val requests = data.requests // List of RequestData objects
                        Log.d("Notification D:" , requests.toString())

                        for (request in requests) {
//                            val requestId = request._id
//                            val senderId = request.senderId
//                            val receiverId = request.receiverId
//                            val teamId = request.teamId
//                            val sessionId = request.sessionId
                            meassage.add(request.message)
                            state.add(request.status)
                            createdAt.add(request.createdAt)
//                            val estimatedAffectees = request.estimatedAffectees
//                            val location = request.location
//                            val redZones = request.redZones
//                            val affectees = request.affectees
                            // Handle the properties accordingly
                        }
                        Log.d("Notification D:" , meassage.toString())
                        Log.d("Notification D:" , state.toString())
                        Log.d("Notification D:" , createdAt.toString())

                        noti_RecyclerView= view.findViewById(R.id.noti_recycle_view)
                        noti_RecyclerView.layoutManager= LinearLayoutManager(requireContext())
                        noti_RecyclerView.setHasFixedSize(true)


                        val adapter = NotificationAdapter(meassage, state, createdAt)
                        noti_RecyclerView.adapter = adapter
                    }

                } else {
                    // Handle the error response here
                }

            }

            override fun onFailure(call: Call<NotificationResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return view;
    }

}