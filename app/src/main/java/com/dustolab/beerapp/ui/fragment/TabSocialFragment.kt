package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.BarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.BeerReviewsUseCase
import com.dustolab.beerapp.logic.usecase.FavoriteBarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.FavoriteBeerReviewsUseCase
import com.dustolab.beerapp.logic.usecase.FavoriteBeerUseCase
import com.dustolab.beerapp.logic.usecase.FollowingBarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.FollowingBeerReviewsUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.PostAdapter

class TabSocialFragment() : Fragment() {

    private var reviewList: ArrayList<Review> = ArrayList<Review>()
    private lateinit var postAdapter: PostAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tab_social, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        postAdapter = PostAdapter(requireContext(), reviewList)
        recyclerView.adapter = postAdapter
        reviewList.clear()
        if(arguments?.getInt(TYPE_TAB)== ALL_REVIEW){
            setUseCase(BarReviewsUseCase(), BarReview::class.java)
            setUseCase(BeerReviewsUseCase(), BeerReview::class.java)
        }
        else if(arguments?.getInt(TYPE_TAB)== FOLLOW_REVIEW){
            val listFollower = arguments?.getStringArrayList(ARRAYLIST_FOLLOWER)
            Log.d("BEER_", "${listFollower}")
            if(!listFollower.isNullOrEmpty()) {
                setUseCase(FollowingBarReviewsUseCase(listFollower!!), BarReview::class.java)
                setUseCase(FollowingBeerReviewsUseCase(listFollower!!), BeerReview::class.java)
            }
            val listFavoriteBar = arguments?.getStringArrayList(ARRAYLIST_FAVORITE_BAR)
            if(!listFavoriteBar.isNullOrEmpty()) {
                setUseCase(FavoriteBarReviewsUseCase(listFavoriteBar!!), BarReview::class.java)
            }
            val listFavoriteBeer = arguments?.getStringArrayList(ARRAYLIST_FAVORITE_BEER)
            if(!listFavoriteBeer.isNullOrEmpty()){
                setUseCase(FavoriteBeerReviewsUseCase(listFavoriteBeer!!), BeerReview::class.java)
            }
        }

    }

    private fun <T:Review> setUseCase(useCase: UseCase, clazz: Class<T>){
        useCase.useCase()
            .addOnSuccessListener {documents->
                documents.forEach { doc->
                    val elem = doc.toObject(clazz)
                    reviewList.add(elem)
                    reviewList.shuffle()
                    postAdapter.notifyDataSetChanged()
                }

            }
    }

    companion object {
        const val ALL_REVIEW = 0
        const val FOLLOW_REVIEW = 1
        const val TYPE_TAB = "TYPE_TAB"
        const val ARRAYLIST_FOLLOWER = "ARRAYLIST_FOLLOWER"
        const val ARRAYLIST_FAVORITE_BAR ="ARRAYLIST_FAVORITE_BAR"
        const val ARRAYLIST_FAVORITE_BEER ="ARRAYLIST_FAVORITE_BEER"
    }

}