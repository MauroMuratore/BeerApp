package com.dustolab.beerapp.ui.fragment

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.ui.adapter.CardBarAdapter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson


class BarsDialogFragment: DialogFragment() {

    private lateinit var barList: List<Bar>
    private lateinit var recyclerViewBars: RecyclerView
    private lateinit var btnClose: Button
    private lateinit var cardBarAdapter: CardBarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_bar_list, container, false)
        var jsonBar = requireArguments()?.getString("bars")
        val sType = object : TypeToken<List<Bar>>() { }.type
        barList = Gson().fromJson<List<Bar>>(jsonBar, sType)

        btnClose = rootView.findViewById(R.id.btn_close)
        recyclerViewBars = rootView.findViewById<RecyclerView>(R.id.recycler_view_bars)

        recyclerViewBars.layoutManager = LinearLayoutManager(context)


        cardBarAdapter = CardBarAdapter(requireContext(), barList)
        recyclerViewBars.adapter = cardBarAdapter

        btnClose.setOnClickListener{
            dismiss()
        }
        return rootView
    }

    override fun onResume() {
        // Set the width of the dialog proportional to 90% of the screen width
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }
}