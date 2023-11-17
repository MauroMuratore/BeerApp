package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.FollowingBarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.FollowingBeerReviewsUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.PostAdapter

class ProfileUserFragment(): Fragment(R.layout.fragment_profile_user) {

    private var reviewList: ArrayList<Review> = ArrayList()
    private lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        tvUsername.text = arguments?.getString(KEY_USER_NAME)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_reviews)
        recyclerView.layoutManager = LinearLayoutManager(context)
        postAdapter = PostAdapter(requireContext(), reviewList, false)
        recyclerView.adapter = postAdapter
        val userUid = arguments?.getString(KEY_USER_UID)
        val listUser = arrayListOf<String>(userUid!!)
        setUseCase(FollowingBarReviewsUseCase(listUser), BarReview::class.java)
        setUseCase(FollowingBeerReviewsUseCase(listUser), BeerReview::class.java)

    }

    private fun <T:Review> setUseCase(useCase: UseCase, clazz: Class<T>){
        useCase.useCase()
            .addOnSuccessListener {documents->
                reviewList.clear()
                documents.forEach { doc->
                    val elem = doc.toObject(clazz)
                    reviewList.add(elem)
                    reviewList.shuffle()
                    postAdapter.notifyDataSetChanged()
                }

                Log.d("BEER_PROFILE_USER", "review list : ${reviewList}")

            }
    }

    companion object{
        const val KEY_USER_UID ="userUid"
        const val KEY_USER_NAME ="username"
    }
}