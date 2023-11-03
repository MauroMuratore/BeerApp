package com.dustolab.beerapp.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dustolab.beerapp.model.User
import com.dustolab.beerapp.ui.fragment.TabSocialFragment

class TabSocialAdapter(
    val fragment: Fragment,
    val user: User
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = TabSocialFragment()
        return if(position==0){
            fragment.arguments = Bundle().apply {
                putInt(TabSocialFragment.TYPE_TAB, TabSocialFragment.ALL_REVIEW)
            }
            fragment
        }else{
            fragment.arguments = Bundle().apply {
                putInt(TabSocialFragment.TYPE_TAB, TabSocialFragment.FOLLOW_REVIEW)
                putStringArrayList(TabSocialFragment.ARRAYLIST_FOLLOWER, user.following)
                putStringArrayList(TabSocialFragment.ARRAYLIST_FAVORITE_BAR, user.favoriteBar)
                putStringArrayList(TabSocialFragment.ARRAYLIST_FAVORITE_BEER, user.favoriteBeers)
            }
            fragment
        }
    }

}