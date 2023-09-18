package com.example.jatayu_sih.apiModelCall

data class NotificationResponse(
    val status: String,
    val data: Data3
)

data class RequestData(
    val _id: String,
    val senderId: String,
    val receiverId: String,
    val teamId: String,
    val sessionId: String,
    val message: String,
    val status: String,
    val createdAt: String,
    val estimatedAffectees: Int, // Adjust the type if needed
    val location: Location,
    val redZones: List<RedZone>,
    val affectees: List<Affectee>
)

data class Location(
    val long: String,
    val lat: String,
    // Add other location properties if needed
)

data class RedZone(
    val long: String,
    val lat: String,
    val severity: Int, // Adjust the type if needed
    val radius: Int // Adjust the type if needed
)

data class Affectee(
    val survivors: Int, // Adjust the type if needed
    val casualities: Int, // Adjust the type if needed
    val injured: Int, // Adjust the type if needed
    val teamId: String,
    val location: Location
)

data class Data3(
    val requests: List<RequestData>
)



