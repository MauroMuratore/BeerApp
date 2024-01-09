package com.dustolab.beerapp.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.MenuEntry
import com.dustolab.beerapp.ui.adapter.CardBarMenuEntryAdapter
import com.google.gson.Gson

class MenuDialogFragment: DialogFragment() {

    private lateinit var bar: Bar
    private lateinit var barMenuFood: List<MenuEntry>
    private lateinit var barMenuDrink: List<MenuEntry>
    private lateinit var recyclerViewFood: RecyclerView
    private lateinit var recyclerViewDrink: RecyclerView
    private lateinit var btnClose: Button
    private lateinit var cardBarMenuEntryAdapter: CardBarMenuEntryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_bar_menu, container, false)
        var jsonBar = requireArguments()?.getString("bar")
        bar = Gson().fromJson(jsonBar, Bar::class.java)
        barMenuFood = bar.food!!
        barMenuDrink = bar.drink!!
        btnClose = rootView.findViewById(R.id.btn_close)
        recyclerViewFood = rootView.findViewById<RecyclerView>(R.id.recycler_view_menu_food)
        recyclerViewDrink = rootView.findViewById<RecyclerView>(R.id.recycler_view_menu_drink)

        recyclerViewFood.layoutManager = LinearLayoutManager(context)
        recyclerViewDrink.layoutManager = LinearLayoutManager(context)


        cardBarMenuEntryAdapter = CardBarMenuEntryAdapter(requireContext(), barMenuFood)
        recyclerViewFood.adapter = cardBarMenuEntryAdapter
        cardBarMenuEntryAdapter = CardBarMenuEntryAdapter(requireContext(), barMenuDrink)
        recyclerViewDrink.adapter = cardBarMenuEntryAdapter

        btnClose.setOnClickListener{
            dismiss()
        }
        return rootView
    }
}