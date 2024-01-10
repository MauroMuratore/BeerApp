package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.dustolab.beerapp.R

class ThemeFragment: Fragment(R.layout.fragment_theme){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = requireView().findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val dayTheme = requireView().findViewById<LinearLayout>(R.id.day_theme)
        val nightTheme = requireView().findViewById<LinearLayout>(R.id.night_theme)

        dayTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        nightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}