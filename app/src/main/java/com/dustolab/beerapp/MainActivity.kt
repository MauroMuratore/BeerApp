package com.dustolab.beerapp

import android.accounts.AccountManager
import android.app.UiModeManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dustolab.beerapp.ui.fragment.BeerListFragment
import com.dustolab.beerapp.ui.fragment.HomeFragment
import com.dustolab.beerapp.ui.fragment.MapFragment
import com.dustolab.beerapp.ui.fragment.SocialFragment
import com.dustolab.beerapp.ui.fragment.UserAreaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity:  AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
            .setupWithNavController(navController)

    }

}