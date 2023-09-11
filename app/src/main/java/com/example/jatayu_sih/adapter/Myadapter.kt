package com.example.jatayu_sih.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.R
import com.example.jatayu_sih.dataClass.Sessions

class Myadapter(private val sessionList: ArrayList<Sessions>):
RecyclerView.Adapter<Myadapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return sessionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=sessionList[position]
        holder.sessionsid.text= currentItem.sessionid
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
      val sessionsid: TextView=itemView.findViewById(R.id.sessionid)
    }

}