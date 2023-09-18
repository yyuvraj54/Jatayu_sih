package com.example.jatayu_sih.nav_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.jatayu_sih.Login
import com.example.jatayu_sih.MainActivity
import com.example.jatayu_sih.R
import com.example.jatayu_sih.loaction.LocationService
import com.example.jatayu_sih.loginStatus


class profile_fragment : Fragment() {

    private lateinit var prefs: loginStatus
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_fragment, container, false)

        prefs = loginStatus(requireContext())
        //val isLoggedIn = prefs.isLoggedIn
        val userId = prefs.userId
        val username = prefs.username.toString()
        val password = prefs.password.toString()
        val token = prefs.token.toString()
        val userRole = prefs.userRole.toString()
        val team = prefs.team.toString()
        val organisation = prefs.organisation.toString()
        val logout=view.findViewById<Button>(R.id.btnLogOut)


        val textViewUserId = view.findViewById<TextView>(R.id.username)
        val textViewUsername = view.findViewById<TextView>(R.id.userId)

        // Set text for TextViews
        textViewUsername.text = "$username"
        textViewUserId.text = "$userId"


        logout.setOnClickListener {
            val prefs = loginStatus(requireContext())
            prefs.isLoggedIn = false
            val intent=Intent(requireContext(),Login::class.java)
            startActivity(intent)
            activity?.finish()

        }

        return view
    }


}