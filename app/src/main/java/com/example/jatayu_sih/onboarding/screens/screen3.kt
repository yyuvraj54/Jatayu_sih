package com.example.jatayu_sih.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.jatayu_sih.Login
import com.example.jatayu_sih.R

class screen3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.fragment_screen3, container, false)


        val finish=view.findViewById<Button>(R.id.thirdbtn)
        finish.setOnClickListener {
          val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            onBoardingFinished()
        }
        return view
    }

    private fun onBoardingFinished(){
        val sharedPref =requireActivity().getSharedPreferences ("onBoarding",Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

}