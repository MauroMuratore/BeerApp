package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import com.dustolab.beerapp.R
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Layer
import androidx.constraintlayout.widget.Group
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
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
import com.dustolab.beerapp.ui.fragment.ProfileUserFragment
import com.google.firebase.firestore.ktx.toObject

class PostAdapter(
    val context: Context,
    val list: List<Review>,
    val displayUser: Boolean = true,
    private val imageRepository: ImageRepository = ImageRepository(),
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PostHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val layerUser = itemView.findViewById<Layer>(R.id.layer_user)
        val tvUser = itemView.findViewById<TextView>(R.id.tv_username)
        val layerReview = itemView.findViewById<Layer>(R.id.layer_review)
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
        Log.d("BEER_REVIEW_POST_ADAPTER", "${review}")
        if(displayUser) {
            val userUseCase = UserUseCase(review.username!!)
            userUseCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach {
                        val user = it.toObject(User::class.java)
                        holder.tvUser.text = user.username
                        holder.layerUser.setOnClickListener { view ->
                            var profile = bundleOf(ProfileUserFragment.KEY_USER_UID to user.uid,
                                ProfileUserFragment.KEY_USER_NAME to user.username)
                            view.findNavController()
                                .navigate(R.id.action_social_to_profile, profile)
                        }
                    }
                }
        }else{
            holder.layerUser.visibility=View.GONE
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
                        holder.tvInfo.text = bar.address?.street
                        holder.layerReview.setOnClickListener { view ->
                            var barUid = bundleOf("uid" to bar.uid)
                            view.findNavController()
                                .navigate(R.id.action_user_profile_to_bar, barUid)
                        }
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
                        holder.layerReview.setOnClickListener { view ->
                            var beer = bundleOf("uid" to beer.uid)
                            view.findNavController()
                                .navigate(R.id.action_user_profile_to_beer, beer)
                        }
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