package com.example.jatayu_sih.retrofit

import com.example.jatayu_sih.User
import com.example.jatayu_sih.apiModelCall.UserLogin
import com.example.jatayu_sih.apiModelCall.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {


    @POST("employee/login")
    fun loginUser(
        @Body userLogin: UserLogin?
    ): Call<UserLoginResponse?>?
}