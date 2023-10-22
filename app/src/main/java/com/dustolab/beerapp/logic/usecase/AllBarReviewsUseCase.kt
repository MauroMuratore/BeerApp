package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BarReviewRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class AllBarReviewsUseCase(
    private val barReviewRepository: BarReviewRepository = BarReviewRepository()
): UseCase {

    override fun useCase(): Task<QuerySnapshot> {
        return barReviewRepository.loadAllBarReview()
    }
}