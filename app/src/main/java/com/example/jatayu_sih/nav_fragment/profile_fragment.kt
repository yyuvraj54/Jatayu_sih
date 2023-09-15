package com.example.jatayu_sih.nav_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.jatayu_sih.Login
import com.example.jatayu_sih.MainActivity
import com.example.jatayu_sih.R
import com.example.jatayu_sih.loaction.LocationService
import com.example.jatayu_sih.loginStatus


class profile_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_fragment, container, false)


        val start=view.findViewById<Button>(R.id.loc_start)
        val stop=view.findViewById<Button>(R.id.loc_stop)
        val logout=view.findViewById<Button>(R.id.btnLogOut)

        logout.setOnClickListener {
            val prefs = loginStatus(requireContext())
            prefs.isLoggedIn = false
            val intent=Intent(requireContext(),Login::class.java)
            startActivity(intent)
            activity?.finish()

        }

        start.setOnClickListener {
            val intent=Intent(requireContext(), LocationService::class.java).apply { action = LocationService.ACTION_START }
            intent.putExtra("troopId", "33543535")
            requireContext().startService(intent)

        }


        stop.setOnClickListener {
            val intent=Intent(requireContext(), LocationService::class.java).apply {
                action = LocationService.ACTION_STOP

            }
            intent.putExtra("troopId", "33543535")
            requireContext().startService(intent)

        }





        return view
    }


}