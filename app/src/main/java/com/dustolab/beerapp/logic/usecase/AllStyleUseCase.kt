package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.StyleRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class AllStyleUseCase (
    private val styleRepository: StyleRepository = StyleRepository()
): UseCase{

    override fun useCase(): Task<QuerySnapshot> {
        return styleRepository.loadAllStyle()
    }

}