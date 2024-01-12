package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.model.Bar

class CardBarAdapter(
    val fragment: Fragment,
    val route: Int,
    val context: Context,
    val barList: List<Bar>,
    private val imageRepository: ImageRepository = ImageRepository()
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class CardBarHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val barImage = itemView.findViewById<ImageView>(R.id.bar_image)
        val barTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val barAddress = itemView.findViewById<TextView>(R.id.tv_address)
        val barRating = itemView.findViewById<RatingBar>(R.id.rating_bar)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_bar, parent, false)
        return CardBarHolder(view)
    }

    override fun getItemCount(): Int {
        return barList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bar = barList[position]
        holder as CardBarHolder

        imageRepository.loadImage(bar.uid!!)
            .addOnSuccessListener { imageByte ->
                val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                holder.barImage.setImageBitmap(bitmap)
            }

        holder.barTitle.text = bar.name
        holder.barAddress.text = bar.address!!.street
        holder.barRating.rating = bar.rating!!

        holder.itemView.setOnClickListener{ view ->
            var useCase = bundleOf("uid" to bar.uid)
            fragment.findNavController()
                .navigate(route, useCase)
        }
    }


}