package com.dustolab.beerapp.ui.fragment

import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.logic.usecase.BarUseCase
import com.dustolab.beerapp.logic.usecase.BeerUseCase
import com.dustolab.beerapp.logic.usecase.NewBeerReviewUseCase
import com.dustolab.beerapp.logic.usecase.UseCase
import com.dustolab.beerapp.model.Bar
import com.dustolab.beerapp.model.BarReview
import com.dustolab.beerapp.model.Beer
import com.dustolab.beerapp.model.BeerReview
import com.dustolab.beerapp.model.Record
import com.dustolab.beerapp.model.Review
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Document
import java.util.Objects

class MakeReview : Fragment(R.layout.fragment_make_review) {
    // TODO: Rename and change types of parameters
    private lateinit var itemImage: ImageView
    private lateinit var itemName: TextView
    private lateinit var itemReview: EditText
    private lateinit var itemRating: RatingBar
    private lateinit var btnSubmit : Button
    private val imageRepository : ImageRepository = ImageRepository()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        var uid: String? = arguments?.getString("uid")
        var type: Int? = arguments?.getInt("type")
        itemImage = view.findViewById(R.id.item_image)
        itemName = view.findViewById(R.id.item_name)
        itemReview = view.findViewById(R.id.ET_review)
        itemRating = view.findViewById(R.id.rating_bar)
        btnSubmit = view.findViewById(R.id.btn_submit)
        setItemInfo(uid, type)
    }

    private fun setItemInfo(uid: String?, type: Int?){
        var useCase: UseCase
        var item: Record
        if (type == 1){
            useCase = BeerUseCase(uid!!)
            useCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach{ doc ->
                        item = doc.toObject(Beer::class.java)
                        imageRepository.loadImage(item.uid!!)
                            .addOnSuccessListener { imageByte->
                                val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                                itemImage.setImageBitmap(bitmap)
                            }
                        itemName.text = item.name
                        btnSubmit.setOnClickListener {
                            submitReview(item)
                        }
                    }
                }
        }
        else{
            useCase = BarUseCase(uid!!)
            useCase.useCase()
                .addOnSuccessListener { documents ->
                    documents.forEach{ doc ->
                        item = doc.toObject(Bar::class.java)
                        imageRepository.loadImage(item.uid!!)
                            .addOnSuccessListener { imageByte->
                                val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                                itemImage.setImageBitmap(bitmap)
                            }
                        itemName.text = item.name
                        btnSubmit.setOnClickListener {
                            submitReview(item)
                        }
                    }
                }
        }
    }
    private fun submitReview(item: Record){
        if (item is Beer){
            var review: BeerReview = BeerReview(
                review = itemReview.text.toString(),
                rating = itemRating.rating,
                username = user!!.uid,
                beer = item.uid)
            var useCase: NewBeerReviewUseCase = NewBeerReviewUseCase(review)
            useCase.useCase()
            Toast.makeText(context, "Review scritta", Toast.LENGTH_LONG).show()
            var bundle = bundleOf("uid" to item.uid)
            view?.findNavController()
                ?.navigate(R.id.review_beer_done, bundle)
        } else{
            var review: BarReview = BarReview(
                review = itemReview.text.toString(),
                rating = itemRating.rating,
                username = user!!.uid,
                bar = item.uid)
            var useCase: NewBeerReviewUseCase = NewBeerReviewUseCase(review)
            useCase.useCase()
            Toast.makeText(context, "Review scritta", Toast.LENGTH_LONG).show()
            var bundle = bundleOf("uid" to item.uid)
            view?.findNavController()
                ?.navigate(R.id.review_bar_done, bundle)
        }

    }
}