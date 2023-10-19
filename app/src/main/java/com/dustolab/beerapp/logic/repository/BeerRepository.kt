package com.dustolab.beerapp.logic.repository

import com.dustolab.beerapp.model.Beer
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BeerRepository(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val dbReference: CollectionReference = Firebase.firestore.collection(PATH)
) {

    suspend fun loadBeer(): List<Beer>{
        return withContext(ioDispatcher) {
            var beerList = ArrayList<Beer>();

            val query = dbReference.get().await()
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