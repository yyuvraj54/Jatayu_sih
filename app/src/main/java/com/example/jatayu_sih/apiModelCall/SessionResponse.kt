package com.example.jatayu_sih.apiModelCall

data class SessionResponse(
    val status: String,
    val data: SessionData
)

data class SessionData(
    val sessions: List<Session>
)

data class Session(
    val _id: String,
    // Define other fields as needed
)