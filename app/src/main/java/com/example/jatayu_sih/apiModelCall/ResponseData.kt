package com.example.jatayu_sih.apiModelCall

data class ResponseData(
    val status: String,
    val data: Data2
)

data class Data2(
    val request: Request
)

data class Request(
    val createdAt: String,
    val teamId: String,
    val estimatedAffectees:Int
)