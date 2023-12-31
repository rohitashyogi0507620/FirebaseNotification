package com.example.firebasenotification.notification

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firebasenotification.CarActivity
import com.example.firebasenotification.MainActivity
import com.example.firebasenotification.MenuClick
import com.example.firebasenotification.ProductActivity
import com.example.firebasenotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Random

class MessagingService : FirebaseMessagingService() {

    var TAG = "NOTIFICATION"
    var bitmap: Bitmap? = null
    lateinit var menuClick: MenuClick


    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("NOTIFICATION", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        menuClick = MenuClick(applicationContext, null, applicationContext as Activity)

        if (remoteMessage != null && remoteMessage.data != null) {

            Log.d("Notification", remoteMessage.data.toString())


            val params:Map<String,String> = remoteMessage.data
            val jsonObject = JSONObject(params)

            var notificationResponse: NotificationResponse = Gson().fromJson(
                jsonObject.toString(),
                object : TypeToken<NotificationResponse>() {}.type
            )
            if (notificationResponse != null) {
                handleNotification(notificationResponse)
            }
        }

    }

    private fun handleNotification(notification: NotificationResponse) {
        var userrolename="Posp"

        if (!notification.role.isNullOrEmpty()){
            if (notification.role.equals(userrolename)){
                showNotification(notification)
            }
        }else{
            showNotification(notification)
        }



    }

    fun showNotification(notification: NotificationResponse){

        var intent: Intent? = null

        if (!notification.productCode.isNullOrEmpty()){

        }
        if (notification.productCode.equals("1")) {
            intent = Intent(this, CarActivity::class.java)
        } else if (notification.productCode.equals(" ")) {
            intent = Intent(this, ProductActivity::class.java)
        } else {
            intent = Intent(this, MainActivity::class.java)
        }
        intent.apply {
            this!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("PRODUCT",notification.extraData)
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, getString(R.string.CHANNEL_ID))
            .setSmallIcon(R.drawable.ic_notification).setContentTitle(notification.title).setContentText(notification.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(100, 300, 300, 300, 300)).setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (notification.imageUrl.isEmpty()) {
            bitmap=getBitmapfromUrl(notification.imageUrl)
            builder.setLargeIcon(bitmap)
            builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
        }

        val notificationId = Random().nextInt(60000)
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(notificationId, builder.build())
            }
        }
    }


    fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }


}