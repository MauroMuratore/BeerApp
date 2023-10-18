package com.dustolab.beerapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dustolab.beerapp.entity.Beer
import com.dustolab.beerapp.logic.BeerRepository
import com.dustolab.beerapp.logic.BeerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ListBeer(
    val listBeer: List<Beer>? = null
)



class HomeViewModel(
    private val beerUseCase: BeerUseCase = BeerUseCase(),
    private val beerRepo: BeerRepository = BeerRepository()
) : ViewModel(){

    private val _uiState = MutableStateFlow(ListBeer())
    val uiState: StateFlow<ListBeer> = _uiState.asStateFlow()

    fun loadBeer() {
        var beerList = ArrayList<Beer>()
        viewModelScope.launch {
            beerList.addAll(beerUseCase.getBeers())
            Log.d("BEER_HOME_VIEW_MODEL", "${beerList}")
        }
    }


}