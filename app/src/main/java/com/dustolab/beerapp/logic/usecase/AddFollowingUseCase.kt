package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.UserRepository

class AddFollowingUseCase(
    val followingUid: String,
    private val userRepository: UserRepository = UserRepository()
    ) {

    fun useCase(){
        userRepository.addFollowing(followingUid)
    }
}