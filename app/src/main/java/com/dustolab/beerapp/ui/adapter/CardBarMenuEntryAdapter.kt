package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.model.MenuEntry
import com.mapbox.maps.extension.style.expressions.dsl.generated.string

class CardBarMenuEntryAdapter(
    private val context: Context,
    private val menu: List<MenuEntry>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CardBarMenuEntryHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvFoodName = itemView.findViewById<TextView>(R.id.tv_food_name)
        val tvFoodCost = itemView.findViewById<TextView>(R.id.tv_food_cost)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_menu_entry, parent, false)
        return CardBarMenuEntryHolder(view)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var entry = menu[position]
        holder as CardBarMenuEntryHolder
        var price = entry.price.toString()
        holder.tvFoodName.text = entry.name
        holder.tvFoodCost.text = price + "0 €"
    }
}