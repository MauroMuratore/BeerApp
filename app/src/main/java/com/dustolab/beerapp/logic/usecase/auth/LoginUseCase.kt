package com.dustolab.beerapp.logic.usecase.auth

import com.dustolab.beerapp.logic.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginUseCase(
    val email : String,
    val password : String,
    private val userRepository: UserRepository = UserRepository()
) {

    fun useCase(): Task<AuthResult>{
        val auth = Firebase.auth
        val result = auth.signInWithEmailAndPassword(email, password)
        result.addOnCompleteListener {
            if(it.isSuccessful){
            }
        }
        return result
    }

}