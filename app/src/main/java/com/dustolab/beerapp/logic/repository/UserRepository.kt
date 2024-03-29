package com.dustolab.beerapp.logic.repository

import android.util.Log
import com.dustolab.beerapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
){
    fun writeNewUser(idUser: String, username: String, email: String){
        val empty = ArrayList<String>()
        val userMap = hashMapOf<String, Any?>(
            UID to idUser,
            USERNAME to username,
            EMAIL to email,
            FAV_BAR to empty,
            FAV_BEER to empty,
            FOLLOWING to empty
        )
        dbReference.document(idUser).set(userMap)
    }
    fun getLocalUser(): Task<DocumentSnapshot> {
        val userUid = Firebase.auth.uid!!
        val ritorno = dbReference.document(userUid).get()
        Log.d("BEER_REPO", "${ritorno}")
        return ritorno
    }

    fun updateUser(){

    }

    fun getUser(uid: String): Task<QuerySnapshot>{
        return dbReference
            .whereEqualTo(UID, uid)
            .get()
    }


    fun addFollowing(followingUid: String){
        val userUid = Firebase.auth.uid!!
        dbReference.document(userUid).update(
            FOLLOWING, FieldValue.arrayUnion(followingUid)
        )
    }

    fun removeFollowing(followingUid: String){
        val userUid = Firebase.auth.uid!!
        dbReference.document(userUid).update(
            FOLLOWING,  FieldValue.arrayRemove(followingUid)
        )
    }

    fun loadFilterUser(filter: Filter): Task<QuerySnapshot> {
        return dbReference
            .where(filter)
            .get()
    }
    fun addFavoriteBeer(beer: String){
        val userUid = Firebase.auth.uid!!
        dbReference.document(userUid).update(FAV_BEER,FieldValue.arrayUnion(beer))
    }

    fun addFavoriteBar(bar: String){
        val userUid = Firebase.auth.uid!!
        dbReference.document(userUid).update(FAV_BAR,FieldValue.arrayUnion(bar))
    }

    fun removeFavoriteBeer(beer: String){
        val userUid = Firebase.auth.uid!!
        dbReference.document(userUid).update(FAV_BEER,FieldValue.arrayRemove(beer))
    }

    fun removeFavoriteBar(bar: String){
        val userUid = Firebase.auth.uid!!
        dbReference.document(userUid).update(FAV_BAR,FieldValue.arrayRemove(bar))
    }

    companion object {
        const val UID : String = "uid"
        const val PATH : String = "users"
        const val USERNAME: String = "username"
        const val EMAIL: String = "email"
        const val FAV_BAR: String ="favorite_bar"
        const val FAV_BEER: String ="favorite_beers"
        const val FOLLOWING: String ="following"

    }

}