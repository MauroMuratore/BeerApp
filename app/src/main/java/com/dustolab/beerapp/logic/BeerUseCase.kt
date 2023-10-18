package com.dustolab.beerapp.logic

import com.dustolab.beerapp.entity.Beer
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class BeerUseCase(
    private val beerRepository: BeerRepository = BeerRepository()
) {

    suspend fun getBeers(): List<Beer>{
        return beerRepository.loadBeer()
    }
}