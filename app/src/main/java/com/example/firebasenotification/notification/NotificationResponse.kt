package com.example.firebasenotification.notification

data class NotificationResponse(
    val body: String,
    val imageUrl: String,
    val response: Response,
    val title: String
)