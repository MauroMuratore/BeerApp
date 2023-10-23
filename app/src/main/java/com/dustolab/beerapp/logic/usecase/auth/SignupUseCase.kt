package com.dustolab.beerapp.logic.usecase.auth

import com.dustolab.beerapp.logic.repository.UserRepository
import com.dustolab.beerapp.logic.usecase.UseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase

class SignupUseCase(
    val username: String,
    val email: String,
    val password: String,
){

    fun useCase(): Task<AuthResult> {
        val auth: FirebaseAuth = Firebase.auth
        val result = auth.createUserWithEmailAndPassword(email, password)
        result.addOnCompleteListener {
            if(it.isSuccessful) {
                val uidUser = auth.currentUser?.uid
                val userRepository = UserRepository()
                userRepository.writeNewUser(uidUser!!, username, email)
            }
        }
        return result
    }

}