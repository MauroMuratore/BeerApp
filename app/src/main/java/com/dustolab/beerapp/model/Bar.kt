package com.dustolab.beerapp.model


data class Bar(
    override var uid: String? = null,
    override var name: String? = null,
    var beerList: List<BeerInBar>? = null,
    var drink: List<MenuEntry>? = null,
    var food: List<MenuEntry>? = null,
    var rating: Float? = null,
    var favoriteBy: List<String>? = null
): Record(){




}