package com.dustolab.beerapp.model

data class User(
    var uid: String? = null,
    var username: String? = null,
    var email: String? = null,
    var favoriteBar : List<String>? = null,
    var favoriteBeers: List<String>? = null
) {
}