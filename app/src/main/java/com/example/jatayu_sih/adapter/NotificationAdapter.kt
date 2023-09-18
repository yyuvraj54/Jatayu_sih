package com.example.jatayu_sih.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.R

class NotificationAdapter(
    private val messages: ArrayList<String>,
    private val states: ArrayList<String>,
    private val createdDates: ArrayList<String>
) :
    RecyclerView.Adapter<NotificationAdapter.RequestViewHolder>() {

    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.noti_desc)
        val stateTextView: TextView = itemView.findViewById(R.id.noti_title)
        val createdAtTextView: TextView = itemView.findViewById(R.id.noti_createdOn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return RequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        // Bind data to the views
        holder.messageTextView.text = "Message: ${messages[position]}"
        holder.stateTextView.text = "State: ${states[position]}"
        holder.createdAtTextView.text = "Created At: ${createdDates[position]}"
    }

    override fun getItemCount() = messages.size
}