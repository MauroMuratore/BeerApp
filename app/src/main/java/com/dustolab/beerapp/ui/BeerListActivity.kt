package com.dustolab.beerapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.AllBeerCaseUse
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.ui.adapter.CardBeerAdapter

class BeerListActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_list)
        val recyclerView = findViewById<RecyclerView>(R.id.all_beer_recycler)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val beerList = ArrayList<Beer>()
        val allBeerUseCase = AllBeerCaseUse()
        allBeerUseCase.useCase()
            .addOnSuccessListener {documents->
                documents.forEach{doc->
                    val elem = doc.toObject(Beer::class.java)
                    beerList.add(elem)
                }

                val cardBeerAdapter = CardBeerAdapter(this, beerList)
                recyclerView.adapter=cardBeerAdapter
            }
    }
}