package com.dustolab.beerapp.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Beer(
    override var uid: String? = null,
    override var name: String? = null,
    var description: String? = null,
    var descriptionIt: String? = null,
    var alcoholContent: Float? = null,
    var style: String? = null,
    var brewery: String? = null,
    var rating: Float? = null,
    var favoriteBy: List<String>? = null

): Record() {
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "uid" to uid,
            "name" to name,
            "description" to description,
            "description_it" to descriptionIt,
            "alcohol_content" to alcoholContent,
            "style" to style,
            "brewery" to brewery,
            "rating" to rating,
            "favorite_by" to favoriteBy
        )
    }
}