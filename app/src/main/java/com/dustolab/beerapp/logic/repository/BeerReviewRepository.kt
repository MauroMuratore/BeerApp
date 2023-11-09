package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeerReviewRepository(
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {

    fun loadAllBeerReview(): Task<QuerySnapshot>{
        return dbReference.get()
    }

    fun loadBeerReview(uid: String, limit : Long = -1):Task<QuerySnapshot>{
        if(limit > 0)
            return dbReference
                .whereEqualTo(BEER, uid)
                .limit(limit)
                .get()
        else
            return dbReference
                .whereEqualTo(BEER, uid)
                .get()
    }

    fun loadUserBeerReview(user : String, limit : Long = -1):Task<QuerySnapshot>{
        if(limit > 0)
            return dbReference
                .whereEqualTo(USER, user)
                .limit(limit)
                .get()
        else
            return dbReference
                .whereEqualTo(USER, user)
                .get()
    }


    companion object{
        const val PATH = "beer_reviews"
        const val BEER : String = "beer"
        const val USER : String = "username"
    }
}