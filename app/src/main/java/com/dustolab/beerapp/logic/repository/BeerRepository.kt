package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class BeerRepository(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {



    fun loadFavoriteBeers(uid: String): Task<QuerySnapshot>{
        return dbReference
            .whereArrayContains(FAVORITE_BY, uid)
            .get()
    }

    fun loadPopularBeers(): Task<QuerySnapshot>{
        return dbReference
            .limit(5)
            .get()
    }

    companion object{
        const val PATH : String = "beers"
        const val ALCOHOL_CONTENT: String = "alcohol_content"
        const val FAVORITE_BY: String = "favorite_by"
    }

}