package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BarReviewRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot

class FavoriteBarReviewsUseCase(
    var listFavorite: List<String>,
    private val barReviewsRepository: BarReviewRepository = BarReviewRepository()
): UseCase{

    override fun useCase(): Task<QuerySnapshot> {
        return barReviewsRepository.loadFilterBarReview(
            Filter.inArray(BarReviewRepository.BAR, listFavorite)
        )
    }

}