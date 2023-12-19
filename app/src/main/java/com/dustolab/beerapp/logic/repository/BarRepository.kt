package com.dustolab.beerapp.logic.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BarRepository(
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {

    fun loadAllBar(): Task<QuerySnapshot>{
        return dbReference.get()
    }

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

    fun addFavBy(user: String, bar: String){
        dbReference.document(bar).update(FAVORITE_BY, FieldValue.arrayUnion(user))
    }

    fun removeFavBy(user: String, bar: String){
        dbReference.document(bar).update(FAVORITE_BY, FieldValue.arrayRemove(user))
    }

    companion object{
        const val UID : String = "uid"
        const val PATH: String = "bar"
        const val RATING: String = "rating"
        const val FAVORITE_BY: String = "favoriteBy"
        const val BEER_LIST: String = "beerList"
    }
}