package com.dustolab.beerapp.model

data class BeerReview(
    override var review: String? = null,
    override var rating: Float? = null,
    override var username: String? = null,
    var beer: String? = null
):  Review(){
}