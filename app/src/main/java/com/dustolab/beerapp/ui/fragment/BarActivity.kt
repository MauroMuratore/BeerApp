package com.dustolab.beerapp.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.ImageRepository
import com.dustolab.beerapp.logic.usecase.BeerUseCase
import com.dustolab.beerapp.model.Bar


class BarActivity() : Fragment(R.layout.fragment_bar_activity) {

    // private var beerUid: String
    private lateinit var barImage: ImageView
    private lateinit var barName: TextView
    private lateinit var barRatingBar: RatingBar
    private lateinit var barDescription: TextView
    private lateinit var barTimetables: TextView
    private lateinit var bar: Bar
    private val imageRepository : ImageRepository = ImageRepository()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var uid: String? = savedInstanceState!!.getString("uid")
        barImage = view.findViewById(R.id.bar_image)
        barName = view.findViewById(R.id.bar_name)
        barRatingBar = view.findViewById(R.id.rating_bar)
        barDescription = view.findViewById(R.id.bar_description)
        barTimetables = view.findViewById(R.id.bar_timetables)
        setBarInfo(uid)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        TODO("Not yet implemented")
    }

    private fun setBarInfo(uid: String?) {

        bar = getBar(uid)

        imageRepository.loadImage(bar.uid!!)
            .addOnSuccessListener { imageByte->
                val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                barImage.setImageBitmap(bitmap)
            }
        barName.text = bar.name
        //barDescription.text = bar.description
        //barTimetables.text = bar.timetables as String
        barRatingBar.rating = bar.rating!!
    }

    private fun getBar(uid: String?): Bar {
        val beerUseCase = BeerUseCase(uid!!)
        beerUseCase.useCase()
            .addOnSuccessListener { documents->
                documents.forEach { doc->
                    bar =  doc.toObject(Bar::class.java)
                }
            }
        return bar
    }
}