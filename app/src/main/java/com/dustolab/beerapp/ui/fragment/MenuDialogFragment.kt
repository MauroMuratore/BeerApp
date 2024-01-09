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
    private lateinit var barMenu: List<MenuEntry>
    private lateinit var recyclerView: RecyclerView
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
        barMenu = bar.food!!
        btnClose = rootView.findViewById(R.id.btn_close)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view_menu)
        recyclerView.layoutManager = LinearLayoutManager(context)

        cardBarMenuEntryAdapter = CardBarMenuEntryAdapter(requireContext(), barMenu)
        recyclerView.adapter = cardBarMenuEntryAdapter
        btnClose.setOnClickListener{
            dismiss()
        }
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        return rootView
    }
}