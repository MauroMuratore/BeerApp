package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BarRepository(
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {

    fun getBar(uid:String): Task<QuerySnapshot>{
        return dbReference
            .whereEqualTo(UID, uid)
            .get()
    }


    fun loadPopularBar(limit : Long = -1): Task<QuerySnapshot>{
        if(limit > 0)
            return dbReference
                .orderBy(RATING,Query.Direction.DESCENDING)
                .limit(limit)
                .get()
        else
            return dbReference
                .orderBy(RATING,Query.Direction.DESCENDING)
                .get()
    }
    companion object{
        const val UID : String = "uid"
        const val PATH: String = "bar"
        const val RATING: String = "rating"
    }
}