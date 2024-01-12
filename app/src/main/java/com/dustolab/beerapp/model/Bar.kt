package com.dustolab.beerapp.model

import java.io.Serializable


data class Bar(
    override var uid: String? = null,
    override var name: String? = null,
    var beerList: List<BeerInBar>? = null,
    var drink: List<MenuEntry>? = null,
    var food: List<MenuEntry>? = null,
    var description: String? = null,
    var timeTables: List<String>? = null,
    var rating: Float? = null,
    var favoriteBy: List<String>? = null,
    var address: Address? = null
): Record(), Serializable{
    fun toStringTimeTables(): String{
        var out: String
        out = "Lunedì: "+timeTables!![0] +"\n" +
                "Martedì: "+timeTables!![1]+"\n" +
                "Mercoledì: "+timeTables!![2]+"\n" +
                "Giovedì: "+timeTables!![3]+"\n" +
                "Venerdì: "+timeTables!![4]+"\n" +
                "Sabato: "+timeTables!![5]+"\n" +
                "Domenica: "+timeTables!![6]
        return out
    }

    fun getBeers(): ArrayList<String>{
        var beers = ArrayList<String>()
        beerList!!.forEach { beer ->
            beers.add(beer.uid!!)
        }
        return  beers
    }

    fun hasBeer(beerUid: String): Boolean{
        beerList?.forEach { beer ->
            if (beer.uid == beerUid)
                return true
        }
        return  false

    }
}