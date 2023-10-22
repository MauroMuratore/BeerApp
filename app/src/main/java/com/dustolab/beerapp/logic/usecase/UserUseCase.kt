package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class UserUseCase(
    val uid: String,
    private val userRepository: UserRepository = UserRepository()
):UseCase {

    override fun useCase(): Task<QuerySnapshot> {
        return userRepository.getUser(uid)
    }
}