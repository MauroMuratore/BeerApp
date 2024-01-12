package com.dustolab.beerapp.model

data class User(
    var uid: String? = null,
    var username: String? = null,
    var email: String? = null,
    var favoriteBar : ArrayList<String>? = null,
    var favoriteBeers: ArrayList<String>? = null,
    var following: ArrayList<String>? = null
)