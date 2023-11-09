package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BeerReviewRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class BeerReviewsUseCase(
    private val beerReviewRepository: BeerReviewRepository = BeerReviewRepository(),
    private val limit : Long = -1,
    private val uid : String? = null

): UseCase {

    override fun useCase(): Task<QuerySnapshot> {
        if(uid.isNullOrBlank())
            return beerReviewRepository.loadAllBeerReview()
        else
            return beerReviewRepository.loadBeerReview(uid, limit)
    }

}