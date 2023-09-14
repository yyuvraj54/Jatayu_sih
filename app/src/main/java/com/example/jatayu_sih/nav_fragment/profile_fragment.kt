package com.example.jatayu_sih.nav_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.jatayu_sih.R
import com.example.jatayu_sih.loaction.LocationService


class profile_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_fragment, container, false)


        val start=view.findViewById<Button>(R.id.loc_start)
        val stop=view.findViewById<Button>(R.id.loc_stop)

        start.setOnClickListener {
            val intent=Intent(requireContext(), LocationService::class.java).apply { action = LocationService.ACTION_START }
            requireContext().startService(intent)

        }

        stop.setOnClickListener {
            val intent=Intent(requireContext(), LocationService::class.java).apply {
                action = LocationService.ACTION_STOP }

            requireContext().startService(intent)

        }





        return view
    }


}