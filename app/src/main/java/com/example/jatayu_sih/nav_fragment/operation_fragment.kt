package com.example.jatayu_sih.nav_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jatayu_sih.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class operation_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_operation_fragment, container, false)

        val supportMapFragment=
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        supportMapFragment.getMapAsync { googleMap: GoogleMap ->

        }
        return  view
    }

}