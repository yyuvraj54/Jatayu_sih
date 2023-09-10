package com.example.jatayu_sih.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.jatayu_sih.R
import com.example.jatayu_sih.SplashActivity
import com.example.jatayu_sih.onboarding.screens.screen1
import com.example.jatayu_sih.onboarding.screens.screen2
import com.example.jatayu_sih.onboarding.screens.screen3
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_view_pager, container, false)



            val fragmentList = arrayListOf<Fragment>(
                screen1(),
                screen2(),
                screen3()

            )

            val adapter = viewPagerAdapter(
                fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle
            )
            val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

            viewPager.adapter = adapter


            val springDotsIndicator = view.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
            springDotsIndicator.attachTo(viewPager)



        return view
    }
    private fun onBoardingFinished():Boolean{
        val sharedPref =requireActivity().getSharedPreferences ("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)

    }




}