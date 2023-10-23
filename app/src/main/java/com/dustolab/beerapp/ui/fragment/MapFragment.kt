package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.AllBarUseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.ui.adapter.CardBarAdapter

class MapFragment: Fragment(R.layout.fragment_map) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView(){
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.bar_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val barList = ArrayList<Bar>()
        val allBarUseCase = AllBarUseCase()
        allBarUseCase.useCase()
            .addOnSuccessListener {documents->
                documents.forEach{ doc->
                    val elem = doc.toObject(Bar::class.java)
                    barList.add(elem)
                }
                val cardBarAdapter = CardBarAdapter(requireContext(), barList)
                recyclerView.adapter = cardBarAdapter
            }
    }
}