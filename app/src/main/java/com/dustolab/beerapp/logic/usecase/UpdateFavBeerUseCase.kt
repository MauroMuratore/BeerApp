package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BeerRepository
import com.dustolab.beerapp.logic.repository.UserRepository

class UpdateFavBeerUseCase(
    private val userRepository: UserRepository = UserRepository(),
    private val beerRepository: BeerRepository = BeerRepository()
) {
    fun addFavorite(user: String, beer: String){
        userRepository.addFavoriteBeer(beer)
        beerRepository.addFavBy(user, beer)

    }

    fun removeFavorite(user: String, beer: String){
        userRepository.removeFavoriteBeer(beer)
        beerRepository.removeFavBy(user, beer)
    }
}