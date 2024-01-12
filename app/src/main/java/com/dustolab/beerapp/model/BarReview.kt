package com.dustolab.beerapp.model

data class BarReview(
    override var review: String? = null,
    override var rating: Float? = null,
    override var username: String? = null,
    var bar: String? = null
    ) : Review()