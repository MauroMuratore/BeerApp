package com.dustolab.beerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.dustolab.beerapp.ui.fragment.BeerListFragment
import com.dustolab.beerapp.ui.fragment.HomeFragment
import com.dustolab.beerapp.ui.fragment.MapFragment
import com.dustolab.beerapp.ui.fragment.SocialFragment
import com.dustolab.beerapp.ui.fragment.UserAreaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity:  AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ( savedInstanceState == null)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container)
            }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.selectedItemId = R.id.home
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.social -> setOnItemSelected(SocialFragment())
                R.id.maps -> setOnItemSelected(MapFragment())
                R.id.home -> setOnItemSelected(HomeFragment())
                R.id.beers -> setOnItemSelected(BeerListFragment())
                R.id.user_area -> setOnItemSelected(UserAreaFragment())
                else -> true
            }
        }

    }

    private fun setOnItemSelected(f: Fragment): Boolean{
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, f)
        }
        return true
    }



}