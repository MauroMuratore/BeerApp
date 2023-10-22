package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepository(
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
){
    fun writeNewUser(idUser: String, username: String, email: String){
        val userMap = hashMapOf<String, Any?>(
            UID to idUser,
            USERNAME to username,
            EMAIL to email,
        )
        dbReference.document(idUser).set(userMap)
    }

    fun updateUser(){

    }

    fun getUser(uid: String): Task<QuerySnapshot>{
        return dbReference
            .whereEqualTo(UID, uid)
            .get()
    }

    companion object {
        const val UID : String = "uid"
        const val PATH : String = "users"
        const val USERNAME: String = "username"
        const val EMAIL: String = "email"
        const val FAV_BAR: String ="favorite_bar"
        const val FAV_BEER: String ="favorite_beers"

    }

}