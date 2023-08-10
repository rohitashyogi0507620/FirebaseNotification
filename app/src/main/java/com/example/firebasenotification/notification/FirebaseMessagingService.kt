package com.example.firebasenotification.notification

import android.Manifest
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

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("NOTIFICATION", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage != null && remoteMessage.data != null) {

            Log.d("Notification", remoteMessage.data.toString())


            var logInResponse: NotificationResponse = Gson().fromJson(
                remoteMessage.data.toString(),
                object : TypeToken<NotificationResponse>() {}.type
            )

            val title = logInResponse.title
            val message = logInResponse.body
            val image = logInResponse.imageUrl
            val data = logInResponse.response

            Log.d("Notification", "Title ${title} Message ${message} Image ${image} Data ${data}")
            if (image != null) {
                bitmap = getBitmapfromUrl(image.toString())
                showNotification(title, message, bitmap, true, data)
            } else {
                showNotification(title, message, bitmap, false, data)
            }
        }

    }

    private fun showNotification(
        title: String?,
        message: String?,
        bitmap: Bitmap?,
        isImage: Boolean,
        data: Response
    ) {
        var intent: Intent? = null


        if (data.PRODUCTCODE.equals("MTRPC")) {
            intent = Intent(this, CarActivity::class.java)
        } else if (data.PRODUCTCODE.equals("MTRTW")) {
            intent = Intent(this, ProductActivity::class.java)
        } else {
            intent = Intent(this, MainActivity::class.java)

        }
        intent.apply {
            this!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("PRODUCT", data.STEP)
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, getString(R.string.CHANNEL_ID))
            .setSmallIcon(R.drawable.ic_notification).setContentTitle(title).setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(100, 300, 300, 300, 300)).setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (isImage) {
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