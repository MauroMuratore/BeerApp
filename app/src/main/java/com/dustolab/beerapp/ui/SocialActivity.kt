package com.dustolab.beerapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.AllBarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.BeerReviewsUseCase
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.PostAdapter

class SocialActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val reviewList = ArrayList<Review>()
        val postAdapter = PostAdapter(this, reviewList)
        recyclerView.adapter = postAdapter
        val allBarReviewsUseCase = AllBarReviewsUseCase()
        val allBeerReviewsUseCase = BeerReviewsUseCase()
        allBarReviewsUseCase.useCase()
            .addOnSuccessListener { documents ->
                documents.forEach { doc ->
                    val elem = doc.toObject(BarReview::class.java)
                    reviewList.add(elem)
                    reviewList.shuffle()
                    postAdapter.notifyDataSetChanged()
                }
            }

        allBeerReviewsUseCase.useCase()
            .addOnSuccessListener {documents->
                documents.forEach { doc->
                    val elem = doc.toObject(BeerReview::class.java)
                    reviewList.add(elem)
                    reviewList.shuffle()
                    postAdapter.notifyDataSetChanged()
                }

            }
    }
}