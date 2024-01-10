package com.dustolab.beerapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.BeerUseCase
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.BeerInBar

class CardBarBeerEntryAdapter(
    private val fragment: Fragment,
    private val context: Context,
    private val beerList: List<BeerInBar>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CardBarBeerEntryHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvBeerName = itemView.findViewById<TextView>(R.id.tv_beer_name)
        val tvBeerStyle = itemView.findViewById<TextView>(R.id.tv_beer_style)
        val tvBeerCost = itemView.findViewById<TextView>(R.id.tv_beer_cost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_beer_entry, parent, false)
        return CardBarBeerEntryHolder(view)
    }

    override fun getItemCount(): Int {
        return beerList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var beer = beerList[position]
        holder as CardBarBeerEntryHolder
        var price = beer.price.toString()
        val useCase = BeerUseCase(beer.uid!!)
        useCase.useCase()
            .addOnSuccessListener { documents->
                documents.forEach{doc ->
                    val item = doc.toObject(Beer::class.java)
                    holder.tvBeerName.text = item.name
                    holder.tvBeerStyle.text = item.style
                }
            }
        holder.tvBeerCost.text = price
        val supp = this
        holder.itemView.setOnClickListener{view ->
            var bundle = bundleOf(
                "uid" to beer.uid
            )
            fragment.findNavController()
                .navigate(R.id.from_beer_in_bar_to_beer, bundle)

        }

    }

}