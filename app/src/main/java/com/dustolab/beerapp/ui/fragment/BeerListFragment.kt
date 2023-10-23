package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import com.dustolab.beerapp.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.logic.usecase.AllBeerUseCase
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.ui.adapter.CardBeerAdapter

class BeerListFragment: Fragment(R.layout.fragment_beer_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilterView()
        setRecyclerView()

    }
    private fun setFilterView(){
        val filterButton = requireView().findViewById<ImageButton>(R.id.filter_button)
        val filterView = requireView().findViewById<LinearLayout>(R.id.filter_view)
        filterButton.setOnClickListener{
            if(filterView.visibility == View.GONE){
                filterView.visibility = View.VISIBLE
            }else{
                filterView.visibility = View.GONE
            }
        }
    }
    private fun setRecyclerView(){
        //creo il recycler view
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.all_beer_recycler)
        recyclerView.layoutManager= LinearLayoutManager(context)
        val beerList = ArrayList<Beer>()
        val allBeerUseCase = AllBeerUseCase()
        allBeerUseCase.useCase()
            .addOnSuccessListener {documents->
                documents.forEach{doc->
                    val elem = doc.toObject(Beer::class.java)
                    beerList.add(elem)
                }
                val cardBeerAdapter = CardBeerAdapter(requireContext(), beerList)
                recyclerView.adapter=cardBeerAdapter
            }
    }
}