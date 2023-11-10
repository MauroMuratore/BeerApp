package com.dustolab.beerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.logic.repository.BeerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListBeer(
    val listBeer: List<Beer>? = null
)



class HomeViewModel(
    private val beerRepo: BeerRepository = BeerRepository()
) : ViewModel(){

    private val _uiState = MutableStateFlow(ListBeer())
    val uiState: StateFlow<ListBeer> = _uiState.asStateFlow()


    fun loadBeer() {
        viewModelScope.launch {
            _uiState.update {currentState ->
                currentState.copy(
                )
            }
        }
    }


}