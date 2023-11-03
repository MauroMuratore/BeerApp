package com.dustolab.beerapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dustolab.beerapp.logic.repository.UserRepository
import com.dustolab.beerapp.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    var user: User? = null

    suspend fun fetchUser(){
            val localUser = userRepository.getLocalUser()
            user = localUser
            Log.d("BEER_VM", "${user}")

    }



}