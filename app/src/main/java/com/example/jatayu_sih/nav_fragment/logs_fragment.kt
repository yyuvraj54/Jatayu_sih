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

class logs_fragment : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Sessions>
    lateinit var sessionid:Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_logs_fragment, container, false)

       sessionid= arrayOf(
           "#123",
           "#234",
           "#456",
           "#567"
       )

        newRecyclerView= view.findViewById(R.id.recview)
        newRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)

        newArrayList= arrayListOf<Sessions>()
        getUserData()

        return view
    }

    private fun getUserData(){
        for(i in sessionid.indices){
            val sessions=Sessions(sessionid[i])
            newArrayList.add(sessions)
        }
        newRecyclerView.adapter=Myadapter(newArrayList)
    }
}