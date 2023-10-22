package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import com.dustolab.beerapp.R
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.logic.repository.BarRepository
import com.dustolab.beerapp.logic.repository.BeerRepository
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.logic.repository.UserRepository
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

class PostAdapter(
    val context: Context,
    val list: List<Review>,
    private val imageRepository: ImageRepository = ImageRepository(),
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PostHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvUser = itemView.findViewById<TextView>(R.id.tv_username)
        val ivReview = itemView.findViewById<ImageView>(R.id.iv_review)
        val tvReviewName = itemView.findViewById<TextView>(R.id.tv_review_name)
        val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.rating_bar)
        val tvReviewDescription = itemView.findViewById<TextView>(R.id.tv_review_description)
        val btnLeggiTutto = itemView.findViewById<Button>(R.id.btn_leggi_tutto)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.post, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as PostHolder
        val review = list[position]
        val userUseCase = UserUseCase(review.username!!)
        userUseCase.useCase()
            .addOnSuccessListener {documents->
                documents.forEach {
                    val user = it.toObject(User::class.java)
                    holder.tvUser.text=user.username
                }
            }
        holder.tvReviewDescription.text = review.review
        holder.ratingBar.rating = review.rating!!
        holder.btnLeggiTutto.setOnClickListener{
           if (holder.tvReviewDescription.maxLines == 1){
               holder.tvReviewDescription.maxLines = 100
               holder.btnLeggiTutto.text = "MOSTRA MENO"
           }else{
               holder.tvReviewDescription.maxLines = 1
               holder.btnLeggiTutto.text = "LEGGI TUTTO"
           }
        }
        if( review is BarReview){
            loadImage(holder, review.bar!!)
            val barUseCase= BarUseCase(review.bar!!)
            barUseCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach {
                        val bar = it.toObject(Bar::class.java)
                        holder.tvReviewName.text = bar.name
                        holder.tvInfo.text = "indirizzo"
                    }
                }

        }
        else if (review is BeerReview){
            loadImage(holder, review.beer!!)
            val beerUseCase = BeerUseCase(review.beer!!)
            beerUseCase.useCase()
                .addOnSuccessListener {documents->
                    documents.forEach {
                        val beer = it.toObject(Beer::class.java)
                        holder.tvReviewName.text = beer.name
                        holder.tvInfo.text = beer.style
                    }

                }
        }
    }

    private fun loadImage(holder: PostHolder, uid: String){
        imageRepository.loadImage(uid)
            .addOnSuccessListener {imageByte->
                val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                holder.ivReview.setImageBitmap(bitmap)
            }
    }

}