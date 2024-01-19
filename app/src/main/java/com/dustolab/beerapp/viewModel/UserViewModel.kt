package com.dustolab.beerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dustolab.beerapp.logic.repository.UserRepository
import com.dustolab.beerapp.model.User
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    fun fetchUser(){
        val taskLocalUser = userRepository.getLocalUser()
        Log.d("BEER_VM", "fetching user")
        taskLocalUser.addOnSuccessListener { doc ->
            val localUser = doc.toObject(User::class.java)!!
            _user.value = localUser
            Log.d("BEER_VM", "${_user.value}")
        }

    }



}