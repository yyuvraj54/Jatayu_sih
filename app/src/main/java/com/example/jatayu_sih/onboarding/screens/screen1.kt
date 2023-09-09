package com.example.jatayu_sih.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.jatayu_sih.R
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class screen1 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_screen1, container, false)


        val viewPager=activity?.findViewById<ViewPager2>(R.id.viewPager)
        val next=view.findViewById<Button>(R.id.firstbtn)
        next.setOnClickListener {
            viewPager?.currentItem=1
        }
        return view
    }

}