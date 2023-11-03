package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BeerReviewRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot

class FollowingBeerReviewsUseCase(
    val listFollowing : List<String>,
    private val beerReviewsRepository: BeerReviewRepository = BeerReviewRepository() ,
) : UseCase {
    override fun useCase(): Task<QuerySnapshot> {
        return beerReviewsRepository.loadFilterBeerReview(
            Filter.inArray(BeerReviewRepository.USERNAME, listFollowing)
        )
    }
}