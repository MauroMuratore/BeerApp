package com.dustolab.beerapp.logic

import android.util.Log
import com.dustolab.beerapp.entity.Beer
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

import java.util.Vector

class BeerRepository(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val database: CollectionReference = Firebase.firestore.collection(PATH)
) {

    suspend fun loadBeer(): List<Beer>{
        return withContext(ioDispatcher) {
            var beerList = ArrayList<Beer>();

            val query = database.get().await()
            query.documents.forEach {
                val beer = it.toObject(Beer::class.java)
                beerList.add(beer!!)
            }
            beerList
        }
    }

    companion object{
        const val PATH : String = "beers"
    }

}