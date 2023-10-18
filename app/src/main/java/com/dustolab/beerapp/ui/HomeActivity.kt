package com.dustolab.beerapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.BeerRepository
import com.dustolab.beerapp.viewModel.HomeViewModel


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val homeViewModel = HomeViewModel()
        homeViewModel.loadBeer()
    }
}