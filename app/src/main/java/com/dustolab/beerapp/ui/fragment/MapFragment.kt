package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.usecase.AllBarUseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.ui.adapter.CardBarAdapter
import com.dustolab.beerapp.ui.map.MapManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class MapFragment: Fragment(R.layout.fragment_map) {

    lateinit var mapManager: MapManager
    lateinit var cardBarAdapter: CardBarAdapter
    lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    val barList = ArrayList<Bar>()
    val cardBarList = ArrayList<Bar>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = requireView().findViewById<FrameLayout>(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.bar_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        cardBarAdapter = CardBarAdapter(requireContext(), cardBarList)
        recyclerView.adapter = cardBarAdapter
        mapManager = MapManager(
            requireView().findViewById(R.id.maps),
            requireContext()
        )
        setUseCase()

        mapManager.showMap()
            .addOnMapClickListener{
                if(cardBarList!=barList) {
                    cardBarList.clear()
                    cardBarList.addAll(barList)
                    cardBarAdapter.notifyDataSetChanged()
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                false
            }
    }

    private fun setUseCase(){
        val allBarUseCase = AllBarUseCase()
        allBarUseCase.useCase()
            .addOnSuccessListener {documents->
                barList.clear()
                cardBarList.clear()
                documents.forEach{ doc->
                    val elem = doc.toObject(Bar::class.java)
                    barList.add(elem)
                    val pam = mapManager.setAnnotation(elem.address!!)
                    pam!!.addClickListener{
                        cardBarList.clear()
                        cardBarList.add(elem)
                        cardBarAdapter.notifyDataSetChanged()
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                        true
                    }
                }
                cardBarList.addAll(barList)
                cardBarAdapter.notifyDataSetChanged()
            }
    }

}