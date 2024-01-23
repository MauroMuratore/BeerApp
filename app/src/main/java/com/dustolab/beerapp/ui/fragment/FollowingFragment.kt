package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.FollowingUsersUseCase
import com.dustolab.beerapp.model.User
import com.dustolab.beerapp.ui.adapter.CardUserAdapter
import com.dustolab.beerapp.viewModel.UserViewModel
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class FollowingFragment(
    private var followingList: ArrayList<User> = ArrayList()
): Fragment(R.layout.fragment_following) {

    private lateinit var cardUserAdapter: CardUserAdapter
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val thisFragment = this
        val tvNoFollowing = thisFragment.requireView()
            .findViewById<TextView>(R.id.tv_no_following)

        userViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if(!user.following.isNullOrEmpty()){
                setRecyclerView(thisFragment, user)
                tvNoFollowing.visibility=View.GONE
            }else{
                tvNoFollowing.visibility=View.VISIBLE
                Log.d("BEER_FOLLOWING", "Lista vuota")
            }
        })
        /*
        lifecycleScope.launch {
            userViewModel.fetchUser()
            val user = userViewModel.user!!
            if(!user.following.isNullOrEmpty()){
                setRecyclerView(thisFragment, user)
                tvNoFollowing.visibility=View.GONE
            }else{
                tvNoFollowing.visibility=View.VISIBLE
                Log.d("BEER_FOLLOWING", "Lista vuota")
            }
        }
         */
    }

    private fun setRecyclerView(thisFragment: Fragment, user: User){
        val useCase = FollowingUsersUseCase(user.following!!)
        val recyclerView = thisFragment.requireView()
            .findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(thisFragment.requireContext())
        cardUserAdapter = CardUserAdapter(thisFragment.requireContext(),
            followingList)
        useCase.useCase()
            .addOnSuccessListener { documents ->
                followingList.clear()
                documents.forEach{doc ->
                    val elem = doc.toObject(User::class.java)
                    followingList.add(elem)
                }
                cardUserAdapter.notifyDataSetChanged()
                recyclerView.adapter=cardUserAdapter
            }
    }

}