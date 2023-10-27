package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import com.dustolab.beerapp.R
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.logic.usecase.FavoriteBeerUseCase
import com.dustolab.beerapp.logic.usecase.PopularBarUseCase
import com.dustolab.beerapp.logic.usecase.PopularBeerUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.Record
import com.dustolab.beerapp.ui.adapter.CardPreviewAdapter

class HomeFragment: Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter(
            FavoriteBeerUseCase(5),
            R.id.favorite_beers_recycler,
            Beer::class.java
        )
        setAdapter(
            PopularBeerUseCase(5),
            R.id.popular_beers_recycler,
            Beer::class.java
        )
        setAdapter(
            PopularBarUseCase(5),
            R.id.popular_bar_recycler,
            Bar::class.java
        )
        val btnFavoriteBeers = requireView().findViewById<Button>(R.id.favorite_beers_btn)
        btnFavoriteBeers.setOnClickListener {
            val useCase = bundleOf(
                BeerListFragment.BEER_LIST_USE_CASE to BeerListFragment.FAVORITE_BEER_USE_CASE )
            view.findNavController()
                .navigate(R.id.action_home_to_beer_list, useCase)
        }

        val btnPopularBeers = requireView().findViewById<Button>(R.id.popular_beers_btn)
        btnPopularBeers.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_home_to_beer_list)
        }
        val btnPopularBar = requireView().findViewById<Button>(R.id.popular_bar_btn)
        btnPopularBar.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_home_to_map)
        }

    }


    private fun <T:Record> setAdapter(useCase: UseCase, recyclerViewId: Int, clazz: Class<T>){
        val recyclerView = requireView().findViewById<RecyclerView>(recyclerViewId)
        recyclerView.layoutManager=LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val list = ArrayList<T>()
        useCase.useCase()
            .addOnSuccessListener { documents->
                documents.forEach { doc->
                    val elem = doc.toObject(clazz)
                    list.add(elem)
                }
                val cardAdapter = CardPreviewAdapter(requireContext(), list)
                recyclerView.adapter=cardAdapter
            }
    }

}