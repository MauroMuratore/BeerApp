package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.model.Record

class CardAdapter(
    val context: Context,
    val recordist: List<Record>,
    private val imageRepository : ImageRepository = ImageRepository()
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class CardHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.beer_image)
        val text = itemView.findViewById<TextView>(R.id.beer_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_beer, parent, false)
        return CardHolder(view)
    }

    override fun getItemCount(): Int {
        return recordist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beer = recordist[position]
        holder as CardHolder

        imageRepository.loadImage(beer.uid!!)
            .addOnSuccessListener { imageByte->
                val bitmap =  BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size);
                holder.image.setImageBitmap(bitmap)
            }

        holder.text.text = beer.name
    }




}