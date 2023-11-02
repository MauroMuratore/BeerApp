package com.dustolab.beerapp.ui.fragment

import android.content.ContentResolver
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.contentColorFor
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.ProviderPosition
import com.dustolab.beerapp.logic.usecase.AllBarUseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.ui.adapter.CardBarAdapter
import com.dustolab.beerapp.ui.map.MapManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class MapFragment: Fragment(R.layout.fragment_map) {

    lateinit var mapManager: MapManager
    lateinit var cardBarAdapter: CardBarAdapter
    lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    lateinit var providerPosition: ProviderPosition
    lateinit var requestPermission: ActivityResultLauncher<Array<String>>
    val barList = ArrayList<Bar>()
    val cardBarList = ArrayList<Bar>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRequestPermission()
        val bottomSheet = requireView().findViewById<FrameLayout>(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.bar_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        cardBarAdapter = CardBarAdapter(requireContext(), cardBarList)
        recyclerView.adapter = cardBarAdapter

        setMapManager()
        setPosition()
        val fabPosition = requireView().findViewById<FloatingActionButton>(R.id.fab_my_position)
        fabPosition.setOnClickListener{
            setPosition()
        }
    }

    private fun setMapManager(){
        mapManager = MapManager(
            requireView().findViewById(R.id.maps),
            requireContext()
        )
        setUseCase()

        mapManager.showMap()
            .addOnMapClickListener{
                if(cardBarList!=barList) {
                    setCardBarList(
                        BottomSheetBehavior.STATE_COLLAPSED,
                        *barList.map { it }.toTypedArray()
                    )
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
                    val pam = mapManager.setAnnotation(
                        elem.address!!.longitude!!, elem.address!!.latitude!!)
                    pam!!.addClickListener{
                        mapManager.setLocation(elem.address!!.longitude!!, elem.address!!.latitude!!)
                        setCardBarList(BottomSheetBehavior.STATE_HALF_EXPANDED, elem)
                        true
                    }
                }
                cardBarList.addAll(barList)
                cardBarAdapter.notifyDataSetChanged()
            }
    }

    private fun setPosition(){
        providerPosition = ProviderPosition(requireContext())
        val listPermissions = listOf<String>(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        when{
            providerPosition.checkPermission() ->{
                setUserPosition()
            }
            else ->{
                requestPermission.launch(
                    listPermissions.toTypedArray()
                )
            }
        }

    }

    private fun setUserPosition(){
        when{
            providerPosition.checkPosition() ->{
                providerPosition.getPosition()
                    .addOnSuccessListener {
                        mapManager.setUserAnnotation(it.longitude, it.latitude)
                        mapManager.setLocation(it.longitude, it.latitude)
                    }
            }
            else ->{
                Toast.makeText(requireContext(),
                    "Accendere il GPS per utilizzare la propria posizione",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setRequestPermission(){
        requestPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){mapGranted ->
            if(!mapGranted.containsValue(false)){
                setUserPosition()
            }
            else{
                Toast.makeText(requireContext(),
                    "Attiva i permessi della posizione per utilizzare la propria posizione",
                    Toast.LENGTH_SHORT).show()
                Log.d("BEER_MAP_FRAGMENT", "NO POSITION")

            }
        }
    }
    private fun setCardBarList(state: Int, vararg bar: Bar){
        cardBarList.clear()
        cardBarList.addAll(bar)
        cardBarAdapter.notifyDataSetChanged()
        bottomSheetBehavior.state = state

    }


}