package com.example.firebasenotification

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson

class MenuClick(var context: Context, var view: View?=null, var activity: Activity) {

    var gson: Gson
    var isLoggedIn = false
    var clickedProduct = 0;

    init {

        gson = Gson()

        if (isLoggedIn) {

        }

    }

    fun MenuItemClick(menuId: Int) {

        if (menuId == 1) {
            Toast.makeText(context,"Logout",Toast.LENGTH_SHORT).show()

        } else if (menuId == 2) {
            Toast.makeText(context,"Commision Bazar",Toast.LENGTH_SHORT).show()

        }
    }

    fun ProductItemClick(productID: Int) {

        clickedProduct = productID;

        if (clickedProduct == 1 ) {
            context.startActivity(Intent(context,ProductActivity::class.java))
        } else if (clickedProduct == 2) {
            context.startActivity(Intent(context,CarActivity::class.java))
        }
    }


}