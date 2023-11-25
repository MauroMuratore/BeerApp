package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.UserRepository
import com.google.firebase.firestore.FieldValue

class RemoveFollowingUseCase(
    val followingUid: String,
    private val userRepository: UserRepository = UserRepository()
    ){

    fun useCase(){
        return userRepository.removeFollowing(followingUid)
    }
}