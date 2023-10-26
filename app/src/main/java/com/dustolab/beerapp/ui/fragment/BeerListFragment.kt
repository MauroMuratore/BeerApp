package com.dustolab.beerapp.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.constraintlayout.widget.Group
import com.dustolab.beerapp.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.logic.usecase.AllBeerUseCase
import com.dustolab.beerapp.logic.usecase.AllStyleUseCase
import com.dustolab.beerapp.logic.usecase.FilterBeerUseCase
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.Style
import com.dustolab.beerapp.ui.adapter.CardBeerAdapter
import com.google.android.material.slider.RangeSlider
import java.util.Arrays

class BeerListFragment(
    var beerList: ArrayList<Beer> = ArrayList()
): Fragment(R.layout.fragment_beer_list) {

    private lateinit var cardBeerAdapter: CardBeerAdapter
    private lateinit var spinnerStyle: Spinner
    private lateinit var sliderAlcoholContent: RangeSlider
    private lateinit var sliderRating: RangeSlider
    private lateinit var checkBoxFavorite: CheckBox
    private lateinit var filterGroup: Group

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidget()
        setFilterView()
        setRecyclerView()
        setSpinnerView()
        setButtonReset()
        setButtonFilter()
        setButtonSearch()
    }

    private fun setWidget(){
        spinnerStyle = requireView().findViewById(R.id.spinner_filter_style)
        sliderAlcoholContent = requireView().findViewById(R.id.rs_alcohol_content)
        sliderRating = requireView().findViewById(R.id.rs_rating)
        checkBoxFavorite = requireView().findViewById(R.id.cb_filter_favorite)
    }
    private fun setFilterView(){
        val filterButton = requireView().findViewById<ImageButton>(R.id.btn_filter_show)
        filterGroup = requireView().findViewById<Group>(R.id.group_filter)
        filterButton.setOnClickListener{
            if(filterGroup.visibility == View.GONE){
                filterGroup.visibility = View.VISIBLE
            }else{
                filterGroup.visibility = View.GONE
            }
        }
    }
    private fun setRecyclerView(){
        //creo il recycler view
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.all_beer_recycler)
        recyclerView.layoutManager= LinearLayoutManager(context)
        cardBeerAdapter = CardBeerAdapter(requireContext(), beerList)
        val allBeerUseCase = AllBeerUseCase()
        allBeerUseCase.useCase()
            .addOnSuccessListener {documents->
                documents.forEach{doc->
                    val elem = doc.toObject(Beer::class.java)
                    beerList.add(elem)
                }
                cardBeerAdapter.notifyDataSetChanged()
                recyclerView.adapter=cardBeerAdapter
            }
    }
    private fun setSpinnerView(){
        val allStyleUseCase = AllStyleUseCase()
        val styleList = ArrayList<String>()
        styleList.add("--Scegli stile--")
        allStyleUseCase.useCase()
            .addOnSuccessListener {documents ->
                documents.forEach { doc ->
                    val elem = doc.toObject(Style::class.java).name
                    styleList.add(elem!!)
                }
                val spinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    styleList
                )
                spinnerStyle.adapter = spinnerAdapter
                spinnerStyle.setSelection(0)
            }
    }

    private fun setButtonReset(){
        val btnReset = requireView().findViewById<Button>(R.id.btn_filter_reset)
        btnReset.setOnClickListener {
            spinnerStyle.setSelection(0)
            val valuesAlcoholContent = ArrayList<Float>()
            valuesAlcoholContent.add(0.0F)
            valuesAlcoholContent.add(15.0F)
            sliderAlcoholContent.setValues(valuesAlcoholContent)
            val valuesRating = ArrayList<Float>()
            valuesRating.add(0.0F)
            valuesRating.add(5.0F)
            sliderRating.setValues(valuesRating)
            checkBoxFavorite.isChecked=false
        }
    }

    private fun setButtonFilter(){
        val btnFilter = requireView().findViewById<Button>(R.id.btn_filter_do)

        btnFilter.setOnClickListener {
            var style: String? = null
            if(spinnerStyle.selectedItemId.toInt() !=0){
                style = spinnerStyle.selectedItem as String
            }
            val rangeAlcoholContent = sliderAlcoholContent.values
            val rangeRatingBar = sliderRating.values
            val checkFavorite = checkBoxFavorite.isChecked

            val filterBeerUseCase = FilterBeerUseCase(
                style,
                rangeRatingBar,
                checkFavorite
            )

            filterBeerUseCase.useCase()
                .addOnSuccessListener { documents->
                    beerList.clear()
                    Log.d("BEER_FILTER", "${documents.size()}")
                    documents.forEach { doc ->
                        val elem = doc.toObject(Beer::class.java)
                        Log.d("BEER_FILTER", "${elem}")
                        if(elem.alcoholContent!! >= rangeAlcoholContent.first()
                            && elem.alcoholContent!! <= rangeAlcoholContent.last())
                            beerList.add(elem)
                    }
                    cardBeerAdapter.notifyDataSetChanged()
                    filterGroup.visibility=View.GONE
                }
        }
    }

    private fun setButtonSearch() {
        val btnSearchName = requireView().findViewById<ImageButton>(R.id.btn_search_name)
        btnSearchName.setOnClickListener {
            searchBeerName()
        }
        val editTextSearchName = requireView().findViewById<EditText>(R.id.et_find_beer)
        editTextSearchName.setOnKeyListener{ _, keyCode, keyEvent ->
            if(keyCode == KeyEvent.KEYCODE_ENTER &&
                keyEvent.action == KeyEvent.ACTION_UP) {
                searchBeerName()
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun searchBeerName(){
        val editTextSearchName = requireView().findViewById<EditText>(R.id.et_find_beer)
        val searchName = editTextSearchName.text.toString()
        if (searchName != "") {
            beerList.clear()
            val allBeerUseCase = AllBeerUseCase()
            allBeerUseCase.useCase()
                .addOnSuccessListener { documents->
                    documents.forEach { doc ->
                        val elem = doc.toObject(Beer::class.java)
                        val contains =elem.name!!.contains(searchName, true)
                        if (contains){
                            beerList.add(elem)
                            cardBeerAdapter.notifyDataSetChanged()
                        }
                    }
                }
        }
    }


}