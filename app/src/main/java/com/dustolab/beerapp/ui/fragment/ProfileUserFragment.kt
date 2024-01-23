package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.dustolab.beerapp.model.User
import com.dustolab.beerapp.ui.adapter.PostAdapter
import com.dustolab.beerapp.viewModel.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileUserFragment : Fragment(R.layout.fragment_profile_user) {

    private val userViewModel : UserViewModel by activityViewModels()
    private var reviewList: ArrayList<Review> = ArrayList()
    private lateinit var postAdapter: PostAdapter
    private lateinit var ivMedaglie: ImageView
    private lateinit var etMedaglie: TextView
    private lateinit var followingButton: ImageButton
    private lateinit var userUid: String
    private lateinit var userName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        userName  = arguments?.getString(KEY_USER_NAME)!!
        tvUsername.text= userName

        // RECENSIONI FATTE
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_reviews)
        recyclerView.layoutManager = LinearLayoutManager(context)
        postAdapter = PostAdapter(requireContext(), reviewList, false)
        recyclerView.adapter = postAdapter
        userUid = arguments?.getString(KEY_USER_UID)!!
        val listUser = arrayListOf<String>(userUid)
        reviewList.clear()
        setUseCase(FollowingBarReviewsUseCase(listUser), BarReview::class.java)
        setUseCase(FollowingBeerReviewsUseCase(listUser), BeerReview::class.java)

        // MEDAGLIE
        ivMedaglie = view.findViewById(R.id.iv_medaglie)
        etMedaglie = view.findViewById(R.id.et_medaglie)

        followingButton = view.findViewById<ImageButton>(R.id.following_button)
        Log.d("BEER_PROFILE", "${Firebase.auth.uid}")
        Log.d("BEER_PROFILE", "${userUid}")
        userViewModel.user.observe(viewLifecycleOwner, Observer { user->
            if(user.uid != userUid)
                setFollowing(user)
        })
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
                if(reviewList.size > 0){
                    val tvNoReviews = requireView().findViewById<TextView>(R.id.tv_no_reviews)
                    tvNoReviews.visibility = View.GONE
                }
                setMedaglie()
                Log.d("BEER_PROFILE_USER", "review list : ${reviewList}")

            }
    }

    private fun setMedaglie(){
        when(reviewList.size){
            in 1..4 ->{
                ivMedaglie.setImageResource(R.drawable.medal_apprendista)
                etMedaglie.text="Apprendista"
            }
            in 5..9 ->{
                ivMedaglie.setImageResource(R.drawable.medal_intermedio)
                etMedaglie.text="Intermedio"
            }
            in 10..Int.MAX_VALUE ->{
                ivMedaglie.setImageResource(R.drawable.medal_intenditore)
                etMedaglie.text="Intenditore"
            }

        }
    }

    private fun setFollowing(user: User){
        followingButton.visibility = View.VISIBLE

        if(userUid in user.following!!){
            followingButton.setImageResource(R.drawable.baseline_person_remove_24)
        }
        /*
                lifecycleScope.launch {
                    userViewModel.fetchUser()
                    if (userUid in userViewModel.user!!.following!!) {
                        followingButton.setImageResource(R.drawable.baseline_person_remove_24)
                    }
                }
        */
        followingButton.setOnClickListener{
            if(userUid in user.following!!){
                val useCase = RemoveFollowingUseCase(userUid)
                useCase.useCase()
                followingButton.setImageResource(R.drawable.baseline_person_add_alt_1_24)
                Toast.makeText(requireContext(), "Hai smesso di seguire ${userName}", Toast.LENGTH_SHORT).show()
            }else{
                val useCase = AddFollowingUseCase(userUid)
                useCase.useCase()
                followingButton.setImageResource(R.drawable.baseline_person_remove_24)
                Toast.makeText(requireContext(), "Hai iniziato a seguire ${userName}", Toast.LENGTH_SHORT).show()
            }
            userViewModel.fetchUser()
        }
        /*
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
         */
    }

    companion object{
        const val KEY_USER_UID ="userUid"
        const val KEY_USER_NAME ="username"
    }
}