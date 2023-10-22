package com.dustolab.beerapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.FavoriteBeerUseCase
import com.dustolab.beerapp.logic.usecase.PopularBarUseCase
import com.dustolab.beerapp.logic.usecase.PopularBeerUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.Record
import com.dustolab.beerapp.ui.adapter.CardPreviewAdapter


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

        val favoriteBeerBtn = findViewById<Button>(R.id.favorite_beers_btn)
        favoriteBeerBtn.setOnClickListener{ startBeerList()}
        val btnSearch = findViewById<ImageButton>(R.id.btn_search)
        btnSearch.setOnClickListener{startSocial()}

    }

    private fun <T:Record> setAdapter(useCase: UseCase, recyclerViewId: Int, clazz: Class<T>){
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        recyclerView.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val list = ArrayList<T>()
        useCase.useCase()
            .addOnSuccessListener { documents->
                Log.d("BEER_ACTIVITY", "${documents.size()}")
                documents.forEach{doc->
                    val elem = doc.toObject(clazz)
                    list.add(elem)
                }
                val cardAdapter = CardPreviewAdapter(this, list)
                recyclerView.adapter=cardAdapter
            }
    }

    fun startBeerList(){
        startActivity(Intent(this, BeerListActivity::class.java))
    }

    fun startSocial(){
        startActivity(Intent(this, SocialActivity::class.java))
    }

}


