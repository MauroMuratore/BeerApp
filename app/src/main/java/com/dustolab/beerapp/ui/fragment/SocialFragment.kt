package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import com.dustolab.beerapp.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dustolab.beerapp.logic.usecase.BarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.BeerReviewsUseCase
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.PostAdapter
import com.dustolab.beerapp.ui.adapter.TabSocialAdapter
import com.dustolab.beerapp.viewModel.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class SocialFragment : Fragment(R.layout.fragment_social){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabs()
    }

    override fun onResume() {
        super.onResume()
        setTabs()
    }

    private fun setTabs(){

        val tabLayout = requireView().findViewById<TabLayout>(R.id.tab_layout)
        val userViewModel: UserViewModel by viewModels()
        val thisFragment = this
        lifecycleScope.launch {
            userViewModel.fetchUser()
            Log.d("BEER_FR", "${userViewModel.user}")
            val user = userViewModel.user!!
            val tabSocialAdapter = TabSocialAdapter(thisFragment, user )
            val viewPager2 = requireView().findViewById<ViewPager2>(R.id.view_pager_2)
            viewPager2.adapter = tabSocialAdapter
            TabLayoutMediator(tabLayout,viewPager2){tab, position ->
                if(position==0){
                    tab.text="Popolari"
                }else{
                    tab.text="Seguiti"
                }
            }.attach()
        }
        requireActivity().onBackPressedDispatcher.addCallback (this){
            requireView().findNavController().navigate(R.id.action_global_home)
        }
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        val reviewList = ArrayList<Review>()
        val postAdapter = PostAdapter(requireContext(), reviewList)
        recyclerView?.adapter = postAdapter
        val barReviewsUseCase = BarReviewsUseCase()
        val allBeerReviewsUseCase = BeerReviewsUseCase()
        barReviewsUseCase.useCase()
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