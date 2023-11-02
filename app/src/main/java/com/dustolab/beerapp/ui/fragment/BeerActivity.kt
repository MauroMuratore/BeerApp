package com.dustolab.beerapp.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.logic.usecase.AllBeerUseCase
import com.dustolab.beerapp.logic.usecase.BeerUseCase
import com.dustolab.beerapp.model.Beer
import com.google.firebase.firestore.ktx.toObject

class BeerActivity() : Fragment(R.layout.fragment_beer_activity) {

    // private var beerUid: String
    private lateinit var beerImage: ImageView
    private lateinit var beerName: TextView
    private lateinit var beerRatingBar: RatingBar
    private lateinit var beerDescription: TextView
    private lateinit var beerGrad: TextView
    private lateinit var beer: Beer
    private val imageRepository : ImageRepository = ImageRepository()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var uid: String? = savedInstanceState!!.getString("uid")
        beerImage = view.findViewById(R.id.beer_image)
        beerName = view.findViewById(R.id.beer_name)
        beerRatingBar = view.findViewById(R.id.rating_beer)
        beerDescription = view.findViewById(R.id.beer_description)
        beerGrad = view.findViewById(R.id.alcohol_grad)
        setBeerInfo(uid)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        TODO("Not yet implemented")
    }

    private fun setBeerInfo(uid: String?) {

        beer = getBeer(uid)

        imageRepository.loadImage(beer.uid!!)
            .addOnSuccessListener { imageByte->
                val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                beerImage.setImageBitmap(bitmap)
            }
        beerName.text = beer.name
        beerDescription.text = beer.description
        beerGrad.text = beer.alcoholContent as String
        beerRatingBar.rating = beer.rating!!
    }

    private fun getBeer(uid: String?): Beer {
        val beerUseCase = BeerUseCase(uid!!)
        beerUseCase.useCase()
            .addOnSuccessListener { documents->
                documents.forEach { doc->
                    beer =  doc.toObject(Beer::class.java)
                }
            }
        return beer
    }
}