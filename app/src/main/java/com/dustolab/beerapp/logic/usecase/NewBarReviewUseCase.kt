package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BarReviewRepository
import com.dustolab.beerapp.model.Review
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewBarReviewUseCase(
    val review: Review,
    private val dbReference: CollectionReference = Firebase.firestore.collection(BarReviewRepository.PATH)

) {
    fun useCase(){
        dbReference.document().set(review)
    }
}