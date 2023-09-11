package com.example.jatayu_sih.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.R

class MyViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.noti_imageView)
    val titleTextView: TextView = itemView.findViewById(R.id.noti_title)
    val descriptionTextView: TextView = itemView.findViewById(R.id.noti_desc)
}