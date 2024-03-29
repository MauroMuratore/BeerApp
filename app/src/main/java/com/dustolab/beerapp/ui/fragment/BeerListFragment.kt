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
import androidx.activity.addCallback
import androidx.constraintlayout.widget.Group
import com.dustolab.beerapp.R
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.logic.usecase.AllBeerUseCase
import com.dustolab.beerapp.logic.usecase.AllStyleUseCase
import com.dustolab.beerapp.logic.usecase.BarBeersUseCase
import com.dustolab.beerapp.logic.usecase.BarUseCase
import com.dustolab.beerapp.logic.usecase.FavoriteBeerUseCase
import com.dustolab.beerapp.logic.usecase.FilterBeerUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.BeerInBar
import com.dustolab.beerapp.model.Style
import com.dustolab.beerapp.ui.adapter.CardBarBeerAdapter
import com.dustolab.beerapp.ui.adapter.CardBeerAdapter
import com.google.android.material.slider.RangeSlider

class BeerListFragment(
    private var beerList: ArrayList<Beer> = ArrayList(),
    var barBeers: List<BeerInBar> = ArrayList()

): Fragment(R.layout.fragment_beer_list) {

    private lateinit var cardBeerAdapter: CardBeerAdapter
    private lateinit var cardBarBeerAdapter: CardBarBeerAdapter
    private lateinit var spinnerStyle: Spinner
    private lateinit var sliderAlcoholContent: RangeSlider
    private lateinit var sliderRating: RangeSlider
    private lateinit var checkBoxFavorite: CheckBox
    private lateinit var filterGroup: Group
    private var barUid: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val typeUseCase = arguments?.getInt(BEER_LIST_USE_CASE)
        barUid = arguments?.getString("uid")
        setWidget(typeUseCase)
        setFilterView()
        setRecyclerView(typeUseCase)
        setSpinnerView()
        setButtonReset()
        setButtonFilter()
        setButtonSearch()
        requireActivity().onBackPressedDispatcher.addCallback (this){
            view.findNavController().navigate(R.id.action_global_beer_list)
        }
    }

    private fun setWidget(typeUseCase: Int?){
        spinnerStyle = requireView().findViewById(R.id.spinner_filter_style)
        sliderAlcoholContent = requireView().findViewById(R.id.rs_alcohol_content)
        sliderRating = requireView().findViewById(R.id.rs_rating)
        checkBoxFavorite = requireView().findViewById(R.id.cb_filter_favorite)
        if(typeUseCase == FAVORITE_BEER_USE_CASE){
            checkBoxFavorite.isChecked=true
        }
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
    private fun setRecyclerView(typeUseCase: Int?){
        //creo il recycler view
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.all_beer_recycler)
        recyclerView.layoutManager= LinearLayoutManager(context)
        cardBeerAdapter = CardBeerAdapter(requireContext(), beerList)
        var useCase : UseCase? = null
        /*
        if(typeUseCase == FAVORITE_BEER_USE_CASE) {
            useCase = FavoriteBeerUseCase()
        }
        if(typeUseCase == ALL_BEER_USE_CASE){
            useCase = AllBeerUseCase()
        }
        else{
            //carico il bar
            val barUseCase = BarUseCase(barUid!!)
            barUseCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach{ doc ->
                        val bar = doc.toObject((Bar::class.java))
                        var beersUid = bar.getBeers()
                        barBeers = bar.beerList!!
                        useCase = BarBeersUseCase(beersUid)
                    }
                }
        }
        */
        //nuovo
        if(typeUseCase == ALL_BAR_BEERS_USE_CASE){
            val barUseCase = BarUseCase(barUid!!)
            barUseCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach { doc ->
                        val bar = doc.toObject((Bar::class.java))
                        var beersUid = bar.getBeers()
                        barBeers = bar.beerList!!
                        useCase = BarBeersUseCase(beersUid)
                        useCase!!.useCase()
                            .addOnSuccessListener { documents ->
                                documents.forEach { doc ->
                                    val elem = doc.toObject(Beer::class.java)
                                    beerList.add(elem)
                                }
                                cardBarBeerAdapter = CardBarBeerAdapter(requireContext(), beerList, barBeers)
                                recyclerView.adapter = cardBarBeerAdapter
                            }
                    }
                }

        }
        else{
            if(typeUseCase == FAVORITE_BEER_USE_CASE) {
                useCase = FavoriteBeerUseCase()
            }
            else
                useCase = AllBeerUseCase()
            useCase!!.useCase()
                .addOnSuccessListener {documents->
                    documents.forEach{doc->
                        val elem = doc.toObject(Beer::class.java)
                        beerList.add(elem)
                    }
                    cardBeerAdapter.notifyDataSetChanged()
                    recyclerView.adapter=cardBeerAdapter
                }
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
            sliderAlcoholContent.values = valuesAlcoholContent
            val valuesRating = ArrayList<Float>()
            valuesRating.add(0.0F)
            valuesRating.add(5.0F)
            sliderRating.values = valuesRating
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


    companion object{
        const val ALL_BEER_USE_CASE : Int = 0
        const val FAVORITE_BEER_USE_CASE: Int = 1
        const val ALL_BAR_BEERS_USE_CASE: Int = 2
        const val BEER_LIST_USE_CASE : String = "beerListUseCase"
    }

}