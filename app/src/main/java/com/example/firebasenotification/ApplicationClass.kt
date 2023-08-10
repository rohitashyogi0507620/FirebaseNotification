package com.example.firebasenotification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.firebasenotification.UserRoleConstant.Companion.ROLE_POSP
import com.example.firebasenotification.UserRoleConstant.Companion.USELIST
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import java.io.IOException

class ApplicationClass : Application() {
    var TAG = "Notification"
    var USERROLE = "POSp"


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)

        try {
            USELIST.forEach {
                if (it.equals(USERROLE)) {
                    Firebase.messaging.subscribeToTopic(USERROLE)
                        .addOnCompleteListener { task ->
                            var msg = "Subscribed "
                            if (!task.isSuccessful) {
                                msg = "Subscribe failed"
                            }
                            Log.d(TAG, USERROLE+msg)
                        }
                } else {
                    Firebase.messaging.unsubscribeFromTopic(it)
                }

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d(TAG, token)
            }

        })
        notificationChannel()

    }

    fun notificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(getString(R.string.CHANNEL_ID), name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }


}