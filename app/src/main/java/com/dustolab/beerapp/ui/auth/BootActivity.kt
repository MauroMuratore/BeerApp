package com.dustolab.beerapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.dustolab.beerapp.MainActivity
import com.dustolab.beerapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BootActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot)
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser == null){
            Log.d("BOOT_ACTIVITY", "No user registered")
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            Log.d("BOOT_ACTIVITY", "User ${currentUser!!.uid} registered")
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}