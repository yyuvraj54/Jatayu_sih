package com.example.jatayu_sih.nav_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.R
import com.example.jatayu_sih.adapter.Myadapter
import com.example.jatayu_sih.apiModelCall.SessionResponse
import com.example.jatayu_sih.dataClass.Sessions
import com.example.jatayu_sih.loginStatus
import com.example.jatayu_sih.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class logs_fragment : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Sessions>
    lateinit var sessionid:List<String>
    private lateinit var prefs: loginStatus
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_logs_fragment, container, false)


//        newRecyclerView= view.findViewById(R.id.recview)
//        newRecyclerView.layoutManager= LinearLayoutManager(requireContext())
//        newRecyclerView.setHasFixedSize(true)
//
//        newArrayList= arrayListOf<Sessions>()
//        prefs = loginStatus(requireContext())
//        val team = prefs.team.toString()
//
//        RetrofitInstance.apiInterface.getSessionsByTeamId(team)?.enqueue(object :
//            Callback<SessionResponse> {
//            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
//                if (response.isSuccessful) {
//                    val sessionResponse = response.body()
//                    if (sessionResponse?.status == "success") {
//                        val sessionIds: List<String> = sessionResponse.data.sessions.map { it._id }
//
//
//                        getUserData(sessionIds)
//
//                    } else {
//                        Toast.makeText(requireContext(),"No Active Session right now ", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    // Handle non-200 response
//                }
//            }
//
//            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
//                Toast.makeText(requireContext(),"Backend glitch", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//
//
//
        return view
    }
//
//    private fun getUserData(sessionIds:List<String>){
//        for (sessionId in sessionIds) {
//            val sessions = Sessions(sessionId)
//            Log.d("session List -->",sessions.toString())
//            newArrayList.add(sessions)
//        }
//        newRecyclerView.adapter = Myadapter(newArrayList)
//    }
}