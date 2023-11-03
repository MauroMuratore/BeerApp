package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BarReviewRepository(
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {

    fun loadAllBarReview(): Task<QuerySnapshot> {
        return dbReference.get()
    }

    fun loadFilterBarReview(filter: Filter): Task<QuerySnapshot>{
        return dbReference
            .where(filter)
            .get()
    }








    companion object{
        const val PATH = "bar_reviews"
        const val USERNAME = "username"
        const val BAR = "bar"
    }
}