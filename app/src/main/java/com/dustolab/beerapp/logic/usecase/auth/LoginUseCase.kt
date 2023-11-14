package com.dustolab.beerapp.logic.usecase.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginUseCase(
    val email : String,
    val password : String,
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