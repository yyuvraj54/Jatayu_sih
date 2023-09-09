package com.example.jatayu_sih

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jatayu_sih.databinding.ActivityLoginBinding
import com.example.jatayu_sih.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginbtn.setOnClickListener {
            val organizationID= binding.etOrganisationID.text.toString()
            val troopID= binding.etTroopId.text.toString()

            database= FirebaseDatabase.getInstance().getReference("Users")
            val User= User(organizationID,troopID)
            database.child(organizationID).setValue(User).addOnSuccessListener {
                binding.etOrganisationID.text.clear()
                binding.etTroopId.text.clear()

                Toast.makeText(this,"Successfully saved", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}