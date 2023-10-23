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

class CardPreviewAdapter(
    val context: Context,
    val recordList: List<Record>,
    private val imageRepository : ImageRepository = ImageRepository()
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class CardHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.bar_image)
        val text = itemView.findViewById<TextView>(R.id.beer_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_preview, parent, false)
        return CardHolder(view)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val record = recordList[position]
        holder as CardHolder

        imageRepository.loadImage(record.uid!!)
            .addOnSuccessListener { imageByte->
                val bitmap =  BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size);
                holder.image.setImageBitmap(bitmap)
            }

        holder.text.text = record.name
    }




}