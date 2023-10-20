package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BeerRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class PopularBeerUseCase(
    val limit : Long = -1,
    private val beerRepository: BeerRepository = BeerRepository()
): UseCase {

    override fun useCase(): Task<QuerySnapshot>{
        return beerRepository.loadPopularBeers(limit)
    }
}