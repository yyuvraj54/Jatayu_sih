package com.example.jatayu_sih.apiModelCall

data class UserLoginResponse(
    val status: String,
    val data: Data
)
data class Data(
    val token: String,
    val user: User
)

data class User(
    val _id: String,
    val Id: String,
    val role: String,
    val __v: Int
)

