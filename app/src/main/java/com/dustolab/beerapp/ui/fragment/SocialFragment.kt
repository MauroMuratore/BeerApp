package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.view.View
import com.dustolab.beerapp.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.logic.usecase.AllBarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.AllBeerReviewsUseCase
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.PostAdapter

class SocialFragment : Fragment(R.layout.fragment_social){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val reviewList = ArrayList<Review>()
        val postAdapter = PostAdapter(requireContext(), reviewList)
        recyclerView.adapter = postAdapter
        val allBarReviewsUseCase = AllBarReviewsUseCase()
        val allBeerReviewsUseCase = AllBeerReviewsUseCase()
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