package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.model.Beer

class CardBeerAdapter(
    val context: Context,
    val beerList: List<Beer>,
    private val imageRepository: ImageRepository = ImageRepository(),
    var onItemClick: ((Beer) -> Unit)? = null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class CardBeerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val beerImage = itemView.findViewById<ImageView>(R.id.beer_image)
        val beerTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val beerAlcoholContent = itemView.findViewById<TextView>(R.id.tv_alcohol_content)
        val beerStyle = itemView.findViewById<TextView>(R.id.tv_style)
        val beerRating = itemView.findViewById<RatingBar>(R.id.rating_beer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_beer, parent, false)
        return CardBeerHolder(view)
    }

    override fun getItemCount(): Int {
        return beerList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beer = beerList[position]
        holder as CardBeerHolder
        Log.d("CARD_BEER_ADAPTER", "${beer}")
        imageRepository.loadImage(beer.uid!!)
            .addOnSuccessListener { imageByte->
                val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                holder.beerImage.setImageBitmap(bitmap)
            }

        holder.beerTitle.text = beer.name
        holder.beerAlcoholContent.text = "${beer.alcoholContent}"
        Log.d("BEER_CARD_ADAPTER", "${beer}")
        holder.beerStyle.text = beer.style
        holder.beerRating.rating = beer.rating!!

        holder.itemView.setOnClickListener{ view ->
            var useCase = bundleOf("uid" to beer.uid)

            view.findNavController()
                .navigate(R.id.from_beer_list_to_beer, useCase)
        }

    }
}