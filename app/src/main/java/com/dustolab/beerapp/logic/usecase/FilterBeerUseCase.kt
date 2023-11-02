package com.dustolab.beerapp.logic.usecase

import android.util.Log
import com.dustolab.beerapp.logic.repository.BeerRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase

class FilterBeerUseCase(
    val style: String?,
    val rangeRating: List<Float>,
    val checkFavorite: Boolean,
    private val beerRepository: BeerRepository = BeerRepository()
): UseCase {

    override fun useCase(): Task<QuerySnapshot> {
        val listFilter = ArrayList<Filter>()
        //Filtro style
        if (style!=null){
            listFilter.add(
                Filter.equalTo(
                    BeerRepository.STYLE,
                    style)
            )
            Log.d("BEER_FILTER", style)
        }
        //Filtro range alcohol content
        listFilter.add(
            Filter.greaterThanOrEqualTo(
                BeerRepository.RATING,
                rangeRating.first()
            )
        )
        Log.d("BEER_FILTER", "${rangeRating.first()}")
        listFilter.add(
            Filter.lessThanOrEqualTo(
                BeerRepository.RATING,
                rangeRating.last()
            )
        )
        Log.d("BEER_FILTER", "${rangeRating.last()}")

        //Filtro favorite
        if(checkFavorite){
            val userUid = Firebase.auth.uid
            listFilter.add(
                Filter.arrayContains(
                    BeerRepository.FAVORITE_BY,
                    userUid
                )
            )
            Log.d("BEER_FILTER", userUid!!)
        }

        return beerRepository.loadFilterBeer(
            Filter.and(*listFilter.toTypedArray())
        )

    }


}