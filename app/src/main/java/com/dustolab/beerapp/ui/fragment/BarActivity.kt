package com.dustolab.beerapp.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.logic.usecase.BarReviewsUseCase
import com.dustolab.beerapp.logic.usecase.BarUseCase
import com.dustolab.beerapp.logic.usecase.UpdateFavBarUseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.CardReviewAdapter
import com.google.firebase.auth.FirebaseAuth


class BarActivity() : Fragment(R.layout.fragment_bar_activity) {

    private lateinit var barImage: ImageView
    private lateinit var barName: TextView
    private lateinit var barRatingBar: RatingBar
    private lateinit var barDescription: TextView
    private lateinit var barTimetables: TextView
    private lateinit var barAddress: TextView
    private lateinit var bar: Bar
    private lateinit var btnMakeReview: Button
    private lateinit var cardReviewAdapter : CardReviewAdapter
    private lateinit var btnFavorite: ImageButton
    private var favoriteStatus: Boolean = false
    private lateinit var btnMoreReview: Button
    private lateinit var btnBarBeers: Button
    private val imageRepository : ImageRepository = ImageRepository()
    private val user = FirebaseAuth.getInstance().currentUser




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var uid: String? = arguments?.getString("uid")
        barImage = view.findViewById(R.id.bar_image)
        barName = view.findViewById(R.id.bar_name)
        barRatingBar = view.findViewById(R.id.rating_bar)
        barDescription = view.findViewById(R.id.bar_description)
        barTimetables = view.findViewById(R.id.bar_timetables)
        barAddress = view.findViewById(R.id.bar_address)
        btnMakeReview = view.findViewById(R.id.btn_make_review)
        btnFavorite = view.findViewById<ImageButton>(R.id.btn_favorite)
        btnMoreReview = view.findViewById(R.id.btn_more_review)
        btnBarBeers = view.findViewById(R.id.btn_bar_beer)
        setBarInfo(uid)
    }

    private fun setRecyclerView() {
        //creo il recycler view
        val reviewList = ArrayList<Review>()
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.reviews)
        recyclerView.layoutManager = LinearLayoutManager(context)
        BarReviewsUseCase(limit = 2, uid = bar.uid).useCase()
            .addOnSuccessListener { documents ->
                documents.forEach { doc ->
                    val elem = doc.toObject(BarReview::class.java)
                    reviewList.add(elem)
                }
                cardReviewAdapter = CardReviewAdapter(requireContext(), reviewList)
                cardReviewAdapter.notifyDataSetChanged()
                recyclerView.adapter = cardReviewAdapter
            }
    }

    private fun setBarInfo(uid: String?) {

        val barUseCase = BarUseCase(uid!!)
        barUseCase.useCase()
            .addOnSuccessListener { documents->
                documents.forEach { doc->
                    bar =  doc.toObject(Bar::class.java)
                    imageRepository.loadImage(bar.uid!!)
                        .addOnSuccessListener { imageByte->
                            val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                            barImage.setImageBitmap(bitmap)
                        }
                    barName.text = bar.name
                    barDescription.text = bar.description
                    barTimetables.text = bar.toStringTimeTables()
                    barRatingBar.rating = bar.rating!!
                    barAddress.text = bar.address?.street
                    checkFavorite()
                    setFavoriteBtn()
                    setRecyclerView()
                    btnMakeReview.setOnClickListener {
                        var useCase = bundleOf("uid" to bar.uid, "type" to 0)
                        view?.findNavController()
                            ?.navigate(R.id.from_bar_to_make_a_review, useCase)
                    }
                    btnFavorite.setOnClickListener {
                        changeFavoriteStatus()
                    }
                    btnMoreReview.setOnClickListener {
                        var useCase = bundleOf("uid" to bar.uid, "type" to 1)
                        view?.findNavController()
                            ?.navigate(R.id.from_bar_to_all_reviews, useCase)
                    }
                    btnBarBeers.setOnClickListener {
                        var useCase = bundleOf("uid" to bar.uid, "beerListUseCase" to 2)
                        view?.findNavController()
                            ?.navigate(R.id.from_bar_to_bar_beers, useCase)
                    }
                }
            }
    }
    private fun changeFavoriteStatus() {
        var useCase: UpdateFavBarUseCase = UpdateFavBarUseCase()
        if (favoriteStatus) {
            favoriteStatus = false
            useCase.removeFavorite(user!!.uid, bar.uid!!)
        } else {
            favoriteStatus = true
            useCase.addFavorite(user!!.uid, bar.uid!!)
        }
        setFavoriteBtn()
    }
    private fun setFavoriteBtn() {
        if (favoriteStatus)
            btnFavorite.setImageResource(R.drawable.baseline_star_24)
        else
            btnFavorite.setImageResource(R.drawable.baseline_star_border_24)
    }
    private fun checkFavorite() {
        if(bar.favoriteBy.isNullOrEmpty())
            favoriteStatus = false
        else
            favoriteStatus = bar.favoriteBy!!.contains(user!!.uid)

    }
}