package com.dustolab.beerapp.logic.usecase

import com.dustolab.beerapp.logic.repository.BeerRepository
import com.dustolab.beerapp.model.Beer

class BeerUseCase(
    private val beerRepository: BeerRepository = BeerRepository()
) {

    suspend fun getBeers(): List<Beer>{
        return beerRepository.loadBeer()
    }
}