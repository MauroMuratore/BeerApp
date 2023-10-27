package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BeerRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase

class FavoriteBeerUseCase(
    val limit: Long = -1,
    private val beerRepository: BeerRepository = BeerRepository()
) : UseCase{

    override fun useCase(): Task<QuerySnapshot>{
        val userUid = Firebase.auth.currentUser!!.uid
        return beerRepository.loadFavoriteBeers(userUid, limit)
    }

}