package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.AddFollowingUseCase
import com.dustolab.beerapp.logic.usecase.FollowingBarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.FollowingBeerReviewsUseCase
import com.dustolab.beerapp.logic.usecase.RemoveFollowingUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.PostAdapter
import com.dustolab.beerapp.viewModel.UserViewModel
import kotlinx.coroutines.launch

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
        reviewList.clear()
        setUseCase(FollowingBarReviewsUseCase(listUser), BarReview::class.java)
        setUseCase(FollowingBeerReviewsUseCase(listUser), BeerReview::class.java)

        val userViewModel: UserViewModel by viewModels()
        val followingButton = view.findViewById<ImageButton>(R.id.following_button)

        lifecycleScope.launch {
            userViewModel.fetchUser()
            if (userUid in userViewModel.user!!.following!!) {
                followingButton.setImageResource(R.drawable.baseline_person_remove_24)
            }
        }
        followingButton.setOnClickListener{
            lifecycleScope.launch {
                userViewModel.fetchUser()
                if(userUid in userViewModel.user!!.following!!){
                    val useCase = RemoveFollowingUseCase(userUid)
                    useCase.useCase()
                    followingButton.setImageResource(R.drawable.baseline_person_add_alt_1_24)
                }else{
                    val useCase = AddFollowingUseCase(userUid)
                    useCase.useCase()
                    followingButton.setImageResource(R.drawable.baseline_person_remove_24)
                }
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

                Log.d("BEER_PROFILE_USER", "review list : ${reviewList}")

            }
    }

    companion object{
        const val KEY_USER_UID ="userUid"
        const val KEY_USER_NAME ="username"
    }
}