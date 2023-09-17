package com.example.jatayu_sih

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Organization
import android.util.Log
import android.widget.Toast
import com.example.jatayu_sih.apiModelCall.UserLogin
import com.example.jatayu_sih.apiModelCall.UserLoginResponse
import com.example.jatayu_sih.databinding.ActivityLoginBinding
import com.example.jatayu_sih.databinding.ActivityMainBinding
import com.example.jatayu_sih.retrofit.RetrofitInstance
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: loginStatus
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.white, theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.white)
        }


        prefs = loginStatus(this@Login)

        if (prefs.isLoggedIn) {
            // User is already logged in, navigate to the main part of the app
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }





        binding.loginbtn.setOnClickListener {





            val troopId= binding.etTroopID.text.toString()
            val password= binding.etPassword.text.toString()

            if(troopId!=null && password!=null) {
                val progressBar=ProgressDialog(this@Login)
                progressBar.setMessage("please wait while connecting to backend")
                progressBar.show()




                val userlogin =  UserLogin(troopId ,password, password)

                RetrofitInstance.apiInterface.loginUser(userlogin)?.enqueue(object : Callback<UserLoginResponse?> {
                    override fun onResponse(
                        call: Call<UserLoginResponse?>,
                        response: Response<UserLoginResponse?>
                    ) {
                        if(response.code().toString()=="200"){
                            val gotResponse=response.body()
                            Log.d("LoginIn", "${response.body().toString()}")
                            if (gotResponse != null) {


                                val status = gotResponse.status
                                val token = gotResponse.data.token
                                val user = gotResponse.data.user
                                val userId = user._id
                                val userRole = user.role
                                val team=user.team
                                val organization=user.organisation

                                if(status=="success"){
                                    onLoginSuccess(troopId,password,status,token,userId,userRole,team,organization)
                                }
                                else{
                                    Toast.makeText(this@Login, "Wrong username or Password", Toast.LENGTH_LONG).show()
                                }


                            }

                        }
                        else if(response.code().toString()=="401"){
                            Toast.makeText(this@Login, "Wrong username or Password", Toast.LENGTH_LONG).show()
                        }
                        progressBar.dismiss()
                    }

                    override fun onFailure(call: Call<UserLoginResponse?>, t: Throwable) {
                        Toast.makeText(this@Login, "${t.localizedMessage}", Toast.LENGTH_LONG)
                            .show()
                        progressBar.dismiss()
                    }
                })

            }
            else{ Toast.makeText(this@Login, "Please Enter valid Data", Toast.LENGTH_LONG)}
        }
    }


    private fun onLoginSuccess(username: String, password: String,  status: String, token: String, userId: String, userRole: String, team: String,organization:  String) {
        prefs.username = username
        prefs.password = password
        prefs.isLoggedIn = true

        prefs.status = status
        prefs.token = token
        prefs.userId = userId
        prefs.userRole =userRole
        prefs.team =team
        prefs.organisation =organization


        // Navigate to the main part of the app
        Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}