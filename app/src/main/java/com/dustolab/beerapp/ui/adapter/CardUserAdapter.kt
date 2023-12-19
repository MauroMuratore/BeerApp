package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.model.User

class CardUserAdapter(
    val context: Context,
    val userList: List<User>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CardUserHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val userTitle = itemView.findViewById<TextView>(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_user, parent, false)
        return  CardUserHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = userList[position]
        holder as CardUserHolder

        holder.userTitle.text = user.username
        holder.itemView.setOnClickListener{view ->
            var useCase = bundleOf(
                "userUid" to user.uid,
                "username" to user.username
            )
            view.findNavController()
                .navigate(R.id.from_following_to_profile_user, useCase )
        }
    }



}