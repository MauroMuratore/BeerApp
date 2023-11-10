package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BarReviewRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class BarReviewsUseCase(
    private val limit: Long = -1,
    private val uid: String? = null,
    private val barReviewRepository: BarReviewRepository = BarReviewRepository()
): UseCase {

    override fun useCase(): Task<QuerySnapshot> {
        if(uid.isNullOrBlank())
            return barReviewRepository.loadAllBarReview()
        else
            return barReviewRepository.loadBarReview(uid, limit)
    }
}