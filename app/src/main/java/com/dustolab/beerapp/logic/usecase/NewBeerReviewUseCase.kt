package com.dustolab.beerapp.logic.usecase

import android.content.ContentValues.TAG
import android.util.Log
import com.dustolab.beerapp.logic.repository.BeerRepository
import com.dustolab.beerapp.logic.repository.BeerReviewRepository
import com.dustolab.beerapp.model.Review
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mapbox.bindgen.None

class NewBeerReviewUseCase(
    val review: Review,
    private val dbReference: CollectionReference = Firebase.firestore.collection(BeerReviewRepository.PATH)

) {
    fun useCase(){
        dbReference.document().set(review)
    }
}