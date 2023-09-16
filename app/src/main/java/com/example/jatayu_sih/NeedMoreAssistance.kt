package com.example.jatayu_sih

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.adapter.RequirementsAdapter
import com.example.jatayu_sih.dataClass.RequirementsData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NeedMoreAssistance : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var sendBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<RequirementsData>
    private lateinit var userAdapter:RequirementsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_more_assistance)
        userList = ArrayList()
        addsBtn = findViewById(R.id.addingBtn)
        sendBtn=findViewById(R.id.btnSend)
        recv = findViewById(R.id.mRecycler)
        userAdapter = RequirementsAdapter(this,userList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        addsBtn.setOnClickListener { addInfo() }



    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        val itemName = v.findViewById<EditText>(R.id.itemName)
        val quantityNo = v.findViewById<EditText>(R.id.quantityNo)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val names = itemName.text.toString()
            val number = quantityNo.text.toString()
            userList.add(RequirementsData("Item: $names","Quantity: $number"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding User Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }
    /**ok now run this */

}