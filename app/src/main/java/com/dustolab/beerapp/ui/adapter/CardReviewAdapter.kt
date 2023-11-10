package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.logic.usecase.BarUseCase
import com.dustolab.beerapp.logic.usecase.BeerUseCase
import com.dustolab.beerapp.logic.usecase.UserUseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.model.User
import com.google.firebase.firestore.ktx.toObject

class CardReviewAdapter(
    val context: Context,
    val list: List<Review>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CardReviewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val username = itemView.findViewById<TextView>(R.id.username)
        val rating_bar = itemView.findViewById<RatingBar>(R.id.rating_bar)
        val review = itemView.findViewById<TextView>(R.id.user_review)
        val btn_leggi_tutto = itemView.findViewById<Button>(R.id.btn_leggi_tutto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item_review, parent, false)
        return CardReviewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val review = list[position]
        holder as CardReviewHolder

        val userUseCase = UserUseCase(review.username!!)
        userUseCase.useCase()
            .addOnSuccessListener { documents ->
                documents.forEach {
                    val user = it.toObject(User::class.java)
                    holder.username.text = user.username
                }
            }
        holder.review.text = review.review
        holder.rating_bar.rating = review.rating!!

        holder.btn_leggi_tutto.setOnClickListener{
            if (holder.review.maxLines == 1){
                holder.review.maxLines = 100
                holder.btn_leggi_tutto.text = "MOSTRA MENO"
            }else{
                holder.review.maxLines = 1
                holder.btn_leggi_tutto.text = "LEGGI TUTTO"
            }
        }

    }
}