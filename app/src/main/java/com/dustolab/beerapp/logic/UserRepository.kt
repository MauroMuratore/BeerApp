package com.dustolab.beerapp.logic

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository() {
    private val database: DatabaseReference = Firebase.database.reference

    fun writeNewUser(idUser: String, username: String, email: String){
        val userMap : Map<String, Any> =mapOf(
            USERNAME to username,
            EMAIL to email
        )
        database.child(PATH).child(idUser).setValue(userMap)
    }

    fun updateUser(){

    }

    fun getUser(){

    }

    companion object {
        const val PATH : String = "users"
        const val USERNAME: String = "username"
        const val EMAIL: String = "email"
        const val FAV_BAR: String ="favorite_bar"
        const val FAV_BEER: String ="favorite_beers"

    }

}