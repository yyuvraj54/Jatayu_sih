package com.example.jatayu_sih.nav_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.R
import com.example.jatayu_sih.adapter.Myadapter
import com.example.jatayu_sih.dataClass.Sessions

class notification_fragment : Fragment() {


    private lateinit var noti_RecyclerView: RecyclerView
    private lateinit var noti_array: ArrayList<Sessions>
    lateinit var sessionid:Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_notification_fragment, container, false)

        
//        noti_RecyclerView= view.findViewById(R.id.noti_recycle_view)
//        noti_RecyclerView.layoutManager= LinearLayoutManager(requireContext())
//        noti_RecyclerView.setHasFixedSize(true)
//
//        noti_array= arrayListOf<Sessions>()
//        getUserData()



        return view;
    }


    private fun getUserData(){
        for(i in sessionid.indices){
            val sessions=Sessions(sessionid[i])
            noti_array.add(sessions)
        }
        noti_RecyclerView.adapter= Myadapter(noti_array)
    }
}