package com.example.jatayu_sih.apiModelCall

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    val status: String,
    val data: Data4
)

data class Data4(
    val sessions: List<Session>
)

data class Session(
    val status: Boolean,
    val _id: String,
    val teamMembers: List<Any>, // You can replace Any with the actual type if needed
    val sessionId: String?, // Replace with the actual type if not nullable
    val requests: List<Any>, // You can replace Any with the actual type if needed
    val resources: List<Any>, // You can replace Any with the actual type if needed
    val Organisation: String, // You can replace String with the actual type if needed
    val redZones: List<Any>, // You can replace Any with the actual type if needed
    val __v: Int
)