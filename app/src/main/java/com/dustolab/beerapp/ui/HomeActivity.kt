package com.dustolab.beerapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dustolab.beerapp.R
import com.dustolab.beerapp.viewModel.HomeViewModel
import kotlinx.coroutines.launch


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val viewModel: HomeViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loadBeer()
                viewModel.uiState.collect{ it ->
                    it.listBeer?.forEach{
                        Log.d("BEER_Activity", it.name!!)
                    }
                }
            }
        }
    }



}