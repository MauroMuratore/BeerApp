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
import com.dustolab.beerapp.logic.usecase.BarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.BeerReviewsUseCase
import androidx.viewpager2.widget.ViewPager2
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
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val userViewModel: UserViewModel by viewModels()
        val thisFragment = this
        lifecycleScope.launch {
            userViewModel.fetchUser()
            Log.d("BEER_FR", "${userViewModel.user}")
            val user = userViewModel.user!!
            val tabSocialAdapter = TabSocialAdapter(thisFragment, user )
            val viewPager2 = view.findViewById<ViewPager2>(R.id.view_pager_2)
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
            view.findNavController().navigate(R.id.action_global_home)
        }

    }


}