package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class BeerListFragmentArgs(
  public val beerListUseCase: Int = 0,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("beerListUseCase", this.beerListUseCase)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("beerListUseCase", this.beerListUseCase)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): BeerListFragmentArgs {
      bundle.setClassLoader(BeerListFragmentArgs::class.java.classLoader)
      val __beerListUseCase : Int
      if (bundle.containsKey("beerListUseCase")) {
        __beerListUseCase = bundle.getInt("beerListUseCase")
      } else {
        __beerListUseCase = 0
      }
      return BeerListFragmentArgs(__beerListUseCase)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): BeerListFragmentArgs {
      val __beerListUseCase : Int?
      if (savedStateHandle.contains("beerListUseCase")) {
        __beerListUseCase = savedStateHandle["beerListUseCase"]
        if (__beerListUseCase == null) {
          throw IllegalArgumentException("Argument \"beerListUseCase\" of type integer does not support null values")
        }
      } else {
        __beerListUseCase = 0
      }
      return BeerListFragmentArgs(__beerListUseCase)
    }
  }
}
