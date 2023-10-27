package com.dustolab.beerapp.ui.fragment

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dustolab.beerapp.R
import com.dustolab.beerapp.ui.auth.LoginActivity
import com.dustolab.beerapp.ui.auth.SignupActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserAreaFragment: Fragment(R.layout.fragment_user_area) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val buttonDayNightMode = requireView().findViewById<FloatingActionButton>(R.id.fab_day_night_mode)
        var currentNightMode = AppCompatDelegate.getDefaultNightMode()
        if(currentNightMode == AppCompatDelegate.MODE_NIGHT_NO){
            buttonDayNightMode.setImageResource(R.drawable.baseline_wb_sunny_24)
        }else{
            buttonDayNightMode.setImageResource(R.drawable.baseline_nightlight_round_24)
        }

        buttonDayNightMode.setOnClickListener{
            var currentNightMode = AppCompatDelegate.getDefaultNightMode()

            if (currentNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                buttonDayNightMode.setImageResource(R.drawable.baseline_nightlight_round_24)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                buttonDayNightMode.setImageResource(R.drawable.baseline_wb_sunny_24)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        val btnLogout = requireView().findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            logout()
        }

    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(context,LoginActivity::class.java))
    }

}