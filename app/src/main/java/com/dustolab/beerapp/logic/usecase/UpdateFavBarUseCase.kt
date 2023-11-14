package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BarRepository
import com.dustolab.beerapp.logic.repository.UserRepository

class UpdateFavBarUseCase(
    private val userRepository: UserRepository = UserRepository(),
    private val barRepository: BarRepository = BarRepository()
) {
    fun addFavorite(user: String, bar: String){
        userRepository.addFavoriteBar(bar)
        barRepository.addFavBy(user, bar)

    }

    fun removeFavorite(user: String, bar: String){
        userRepository.removeFavoriteBar(bar)
        barRepository.removeFavBy(user, bar)
    }
}