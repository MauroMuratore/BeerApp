package com.dustolab.beerapp.model

data class Beer(
    override var uid: String? = null,
    override var name: String? = null,
    var description: String? = null,
    var descriptionIt: String? = null,
    var alcoholContent: Double? = null,
    var style: String? = null,
    var brewery: String? = null,
    var favoriteBy: List<String>? = null

): Record() {

}