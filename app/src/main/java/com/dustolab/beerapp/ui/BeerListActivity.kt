package com.dustolab.beerapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.AllBeerUseCase
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.ui.adapter.CardBeerAdapter

class BeerListActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_list)

        setRecyclerView()
        setFilterView()

    }

    private fun setFilterView(){
        val filterButton = findViewById<ImageButton>(R.id.filter_button)
        val filterView = findViewById<LinearLayout>(R.id.filter_view)
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
        val recyclerView = findViewById<RecyclerView>(R.id.all_beer_recycler)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val beerList = ArrayList<Beer>()
        val allBeerUseCase = AllBeerUseCase()
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