package com.dustolab.beerapp.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.logic.usecase.BeerReviewsUseCase
import com.dustolab.beerapp.logic.usecase.BeerUseCase
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Review
import com.dustolab.beerapp.ui.adapter.CardReviewAdapter

class BeerActivity() : Fragment(R.layout.fragment_beer_activity) {

    private lateinit var beerImage: ImageView
    private lateinit var beerName: TextView
    private lateinit var beerRatingBar: RatingBar
    private lateinit var beerDescription: TextView
    private lateinit var beerGrad: TextView
    private lateinit var beer: Beer
    private lateinit var cardReviewAdapter : CardReviewAdapter
    private val imageRepository : ImageRepository = ImageRepository()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var uid: String? = arguments?.getString("uid")
        beerImage = view.findViewById(R.id.beer_image)
        beerName = view.findViewById(R.id.beer_name)
        beerRatingBar = view.findViewById(R.id.rating_bar)
        beerDescription = view.findViewById(R.id.beer_description)
        beerGrad = view.findViewById(R.id.alcohol_grad)
        setBeerInfo(uid)
    }

    private fun setRecyclerView() {
        //creo il recycler view
        val reviewList = ArrayList<Review>()
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.reviews)
        recyclerView.layoutManager= LinearLayoutManager(context)
        BeerReviewsUseCase(limit = 2, uid = beer.uid).useCase()
            .addOnSuccessListener {documents->
                documents.forEach{doc->
                    val elem = doc.toObject(BeerReview::class.java)
                    reviewList.add(elem)
                }
                cardReviewAdapter = CardReviewAdapter(requireContext(), reviewList)
                cardReviewAdapter.notifyDataSetChanged()
                recyclerView.adapter=cardReviewAdapter
            }
    }

    private fun setBeerInfo(uid: String?) {

        val beerUseCase = BeerUseCase(uid!!)
        beerUseCase.useCase()
            .addOnSuccessListener { documents->
                documents.forEach { doc->
                    beer =  doc.toObject(Beer::class.java)
                    imageRepository.loadImage(beer.uid!!)
                        .addOnSuccessListener { imageByte->
                            val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                            beerImage.setImageBitmap(bitmap)
                        }
                    beerName.text = beer.name
                    beerDescription.text = beer.description
                    beerGrad.text = beer.alcoholContent.toString()
                    beerRatingBar.rating = beer.rating!!
                    setRecyclerView()
                }
            }
    }
    /*
    private fun getBeer(uid: String?): Beer{
        val beerUseCase = BeerUseCase(uid!!)
        var item : Beer
        beerUseCase.useCase()
            .addOnSuccessListener { documents->
                documents.forEach { doc->
                   item =  doc.toObject(Beer::class.java)
                }
            }
        return item
    }
     */
}