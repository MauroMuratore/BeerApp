package com.dustolab.beerapp.entity

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Beer(
    var uid: String? = null,
    var name: String? = null,
    var description: String? = null,
    var descriptionIt: String? = null,
    var alcoholContent: Double? = null,
    var style: String? = null,
    var brewery: String? = null
){

    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "uid" to uid,
            "name" to name,
            "description" to description,
            "description_it" to descriptionIt,
            "alcoholContent" to alcoholContent,
            "style" to style,
            "brewery" to brewery
        )
    }

    fun toString1(): String? {
        return name
    }
}