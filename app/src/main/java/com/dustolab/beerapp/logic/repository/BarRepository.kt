package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BarRepository(
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {

    fun loadPopularBar(): Task<QuerySnapshot>{
        return dbReference
            .limit(5)
            .get()
    }
    companion object{
        const val PATH: String = "bar"
    }
}