package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.dustolab.beerapp.R
import androidx.fragment.app.Fragment
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