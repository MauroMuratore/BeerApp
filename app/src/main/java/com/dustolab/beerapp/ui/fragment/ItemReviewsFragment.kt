package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.BarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.BeerReviewsUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.CardReviewAdapter

class ItemReviewsFragment() : Fragment(R.layout.fragment_tab_social) {
    private lateinit var reviews: ArrayList<Review>
    private lateinit var cardReviewAdapter: CardReviewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var itemUid = arguments?.getString("uid")
        var type = arguments?.getInt("type")
        getReviews(itemUid,type)
    }

    private fun setRecyclerView() {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager= LinearLayoutManager(context)
        cardReviewAdapter = CardReviewAdapter(requireContext(), reviews)
        recyclerView.adapter = cardReviewAdapter

    }

    private fun getReviews(uid: String?, type: Int?) {
        reviews = ArrayList<Review>()
        if(type == 0){
            var useCase = BeerReviewsUseCase(uid = uid)
            useCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach { doc ->
                        var elem = doc.toObject(BeerReview::class.java)
                        reviews.add(elem)
                    }
                    setRecyclerView()
                }
        }else{
            var useCase = BarReviewsUseCase(uid = uid)
            useCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach { doc ->
                        var elem = doc.toObject(BarReview::class.java)
                        reviews.add(elem)
                    }
                    setRecyclerView()
                }
        }
    }

}