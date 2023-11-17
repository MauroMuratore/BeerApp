package com.dustolab.beerapp

import android.accounts.AccountManager
import android.app.UiModeManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dustolab.beerapp.logic.repository.UserRepository
import com.dustolab.beerapp.ui.fragment.BeerListFragment
import com.dustolab.beerapp.ui.fragment.HomeFragment
import com.dustolab.beerapp.ui.fragment.MapFragment
import com.dustolab.beerapp.ui.fragment.SocialFragment
import com.dustolab.beerapp.ui.fragment.UserAreaFragment
import com.dustolab.beerapp.viewModel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity(
):  AppCompatActivity(R.layout.activity_main) {

    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BEER_", "activity c'è")
        lifecycleScope.launch {
             viewModel.fetchUser()
             Log.d("BEER_", "${viewModel.user}")
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        NavigationUI.setupWithNavController(bottomNavigationView, navController, false)

    }


}