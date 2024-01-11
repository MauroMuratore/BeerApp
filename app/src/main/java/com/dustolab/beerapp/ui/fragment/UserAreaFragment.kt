package com.dustolab.beerapp.ui.fragment

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.dustolab.beerapp.R
import com.dustolab.beerapp.ui.auth.LoginActivity
import com.dustolab.beerapp.ui.auth.SignupActivity
import com.dustolab.beerapp.viewModel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserAreaFragment: Fragment(R.layout.fragment_user_area) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val userViewModel: UserViewModel by viewModels()
            userViewModel.fetchUser()
            val titleUser = requireView().findViewById<TextView>(R.id.title_user)
            titleUser.text = userViewModel.user!!.username

        }

        val btnGestioneProfilo = requireView().findViewById<Button>(R.id.btn_gestione_profilo)
        btnGestioneProfilo.setOnClickListener {
            lifecycleScope.launch {
                val userViewModel: UserViewModel by viewModels()
                userViewModel.fetchUser()
                val bundleUser = bundleOf("userUid" to Firebase.auth.uid,
                    "username" to userViewModel.user!!.username)
                view.findNavController().navigate(R.id.from_user_area_to_profile_user, bundleUser)
            }
        }

        val btnFollowing = requireView().findViewById<Button>(R.id.btn_following)
        btnFollowing.setOnClickListener{
            view.findNavController().navigate(R.id.from_user_area_to_following)
        }

        val btnSettings = requireView().findViewById<Button>(R.id.btn_impostazioni)
        btnSettings.setOnClickListener {
            view.findNavController().navigate(R.id.from_user_area_to_theme)
        }

        val btnLogout = requireView().findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            logout()
        }
        requireActivity().onBackPressedDispatcher.addCallback (this){
            view.findNavController().navigate(R.id.action_global_home)
        }

    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(context,LoginActivity::class.java))
    }

}