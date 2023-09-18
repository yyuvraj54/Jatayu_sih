package com.example.jatayu_sih


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.adapter.RequirementsAdapter
import com.example.jatayu_sih.apiModelCall.ResponseData
import com.example.jatayu_sih.dataClass.RequirementsData
import com.example.jatayu_sih.websocket.SocketHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import io.socket.client.Ack
import org.json.JSONObject

class NeedMoreAssistance : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var sendBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<RequirementsData>
    private lateinit var userAdapter:RequirementsAdapter
    private lateinit var prefs: loginStatus
    private lateinit var message: String
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
        message="Need Assistance"



        prefs = loginStatus(this@NeedMoreAssistance)
        //val isLoggedIn = prefs.isLoggedIn
//        val userId = prefs.userId
//        val username = prefs.username.toString()
//        val password = prefs.password.toString()
//        val token = prefs.token.toString()
//        val userRole = prefs.userRole.toString()
        val team = prefs.team.toString()
        val organisation = prefs.organisation.toString()
        val lat = prefs.lat.toString()
        val long = prefs.long.toString()


        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        val socket = SocketHandler.getSocket()

        sendBtn.setOnClickListener {

            val requestData = JSONObject()
            requestData.put("senderId", "${team}")
            requestData.put("receiverId", "${organisation}")
            requestData.put("teamId", "${team}")
            requestData.put("message", "${message}")
            requestData.put("status", "pending")
            requestData.put("estimatedAffectees", 0)

            val locationData = JSONObject()
            locationData.put("long", "${long.toString()}")
            locationData.put("lat", "${lat.toString()}")
            locationData.put("radius", 50)
            requestData.put("location", locationData)




            socket.emit("req-from-emp", requestData , Ack { args ->
                // Process the callback response here
                if (args.isNotEmpty()) {
                    val response = args[0] // Assuming the response is the first argument
                    println("Received response: $response")
                    Log.d("Need More SocketIO", "Received response: $response")

                }


            })
        }



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

            message= userList.joinToString("\n") { "${it.itemName},${it.quantity}" }
            Log.d("ArrayValues",message)
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