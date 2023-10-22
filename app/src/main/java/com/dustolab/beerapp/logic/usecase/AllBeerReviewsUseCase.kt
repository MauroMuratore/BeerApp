package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BeerReviewRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class AllBeerReviewsUseCase(
    private val beerReviewRepository: BeerReviewRepository = BeerReviewRepository()
): UseCase {

    override fun useCase(): Task<QuerySnapshot> {
       return beerReviewRepository.loadAllBeerReview()
    }
}