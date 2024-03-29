package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class BeerRepository(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH),
    private val barDbReference: CollectionReference = Firebase.firestore.collection((BAR_PATH))
) {


    fun loadAllBeer(): Task<QuerySnapshot>{
        return dbReference
            .orderBy(RATING, Query.Direction.DESCENDING)
            .get()
    }

    fun getBeer(uid: String): Task<QuerySnapshot>{
        return dbReference
            .whereEqualTo(UID, uid)
            .get()
    }

    fun getBeerBars(uid: String): Task<QuerySnapshot> {
        return barDbReference
            .whereEqualTo(BAR_BEERS,uid)
            .get()
    }

    fun searchNameBeer(filter: Filter): Task<QuerySnapshot>{
        return dbReference
            .where(filter)
            .get()
    }

    fun loadFilterBeer(filter: Filter): Task<QuerySnapshot>{
        return dbReference
            .where(filter)
            .orderBy(RATING, Query.Direction.DESCENDING)
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

    fun loadSelectedBeers(uids: ArrayList<String>): Task<QuerySnapshot>{
        return dbReference
            .whereIn(UID, uids)
            .get()
    }

    fun addFavBy(user: String, beer: String){
        dbReference.document(beer).update(FAVORITE_BY, FieldValue.arrayUnion(user))
    }

    fun removeFavBy(user: String, beer: String){
        dbReference.document(beer).update(FAVORITE_BY, FieldValue.arrayRemove(user))
    }

    companion object{
        const val UID : String = "uid"
        const val PATH : String = "beers"
        const val BAR_PATH: String = "bars"
        const val BAR_BEERS: String = "beerList/uid"
        const val FAVORITE_BY: String = "favoriteBy"
        const val RATING: String = "rating"
        const val STYLE : String = "style"
        const val ALCOHOL_CONTENT : String = "alcoholContent"
        const val NAME: String = "name"
    }


}