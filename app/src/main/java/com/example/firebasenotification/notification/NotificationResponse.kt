package com.example.firebasenotification.notification

data class NotificationResponse(
    val title: String,
    val body: String,
    val imageUrl: String,
    val redirectionurl: String,
    val productCode: String,
    val customNotificationid: String,
    val click_action: String,
    val menuCode: String,
    val extraData: String,
)