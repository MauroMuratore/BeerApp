package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class BeerRepository(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {


    fun loadAllBeer(): Task<QuerySnapshot>{
        return dbReference
            .get()
    }

    fun loadFavoriteBeers(uid: String, limit: Long = -1): Task<QuerySnapshot>{
        if(limit > 0)
            return dbReference
                .whereArrayContains(FAVORITE_BY, uid)
                .limit(limit)
                .get()
        else
            return dbReference
                .whereArrayContains(FAVORITE_BY, uid)
                .get()
    }

    fun loadPopularBeers(limit: Long = -1): Task<QuerySnapshot>{
        if(limit >0)
            return dbReference
                .orderBy(RATING,Query.Direction.DESCENDING)
                .limit(limit)
                .get()
        else
            return dbReference
                .orderBy(RATING, Query.Direction.DESCENDING)
                .get()
    }

    companion object{
        const val PATH : String = "beers"
        const val FAVORITE_BY: String = "favoriteBy"
        const val RATING: String = "rating"
    }

}