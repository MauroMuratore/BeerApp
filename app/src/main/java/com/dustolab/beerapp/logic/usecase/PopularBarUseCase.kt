package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BarRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class PopularBarUseCase (
    private val barRepository: BarRepository = BarRepository()
): UseCase {

    override fun useCase(): Task<QuerySnapshot> {
       return barRepository.loadPopularBar()
    }

}