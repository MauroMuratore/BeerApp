package com.dustolab.beerapp.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.BeerInBar
import com.dustolab.beerapp.ui.adapter.CardBarBeerAdapter
import com.dustolab.beerapp.ui.adapter.CardBarBeerEntryAdapter
import com.google.gson.Gson

class BeerDialogFragment: DialogFragment() {

    private var recommendedBeerStatus = false

    private lateinit var bar: Bar
    private lateinit var barBeer: ArrayList<BeerInBar>
    private lateinit var recyclerList: ArrayList<BeerInBar>
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnClose: Button
    private lateinit var btnConsiglia: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var rootView: View = inflater.inflate(R.layout.fragment_beer_in_bar, container, false)

        var jsonBar = requireArguments().getString("bar")
        bar = Gson().fromJson(jsonBar, Bar::class.java)
        barBeer = ArrayList<BeerInBar>()
        barBeer.addAll(bar.beerList!!)

        Log.d("BEER_DIALOG_FRAGMENT", "${barBeer}")
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerList = ArrayList<BeerInBar>()
        recyclerList.addAll(barBeer)
        val cardBarBeerEntryAdapter = CardBarBeerEntryAdapter(this, requireContext(), recyclerList)
        recyclerView.adapter = cardBarBeerEntryAdapter

        btnConsiglia = rootView.findViewById<Button>(R.id.btn_consiglia_birra)
        btnConsiglia.setOnClickListener {
            if(recommendedBeerStatus){
                recommendedBeerStatus = false
                recyclerList.clear()
                recyclerList.addAll(barBeer)
                Log.d("BEER_DIALOG_FRAGMENT", "${barBeer}")
                cardBarBeerEntryAdapter.notifyDataSetChanged()
                btnConsiglia.text =  "Consiglia"
            }
            else{
                recommendedBeerStatus = true
                val recommendedBeer = barBeer.random()
                recyclerList.clear()
                recyclerList.add(recommendedBeer)
                Log.d("BEER_DIALOG_FRAGMENT", "${barBeer}")
                cardBarBeerEntryAdapter.notifyDataSetChanged()
                btnConsiglia.text = "Mostra tutte le birre"
            }

        }

        btnClose = rootView.findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            dismiss()
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return  rootView
    }
}