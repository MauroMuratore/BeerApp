package com.dustolab.beerapp.ui.fragment

import androidx.navigation.NavDirections
import com.dustolab.beerapp.AppNavGraphDirections
import kotlin.Int

public class MapFragmentDirections private constructor() {
  public companion object {
    public fun actionGlobalHome(): NavDirections = AppNavGraphDirections.actionGlobalHome()

    public fun actionGlobalSocial(): NavDirections = AppNavGraphDirections.actionGlobalSocial()

    public fun actionGloablMap(): NavDirections = AppNavGraphDirections.actionGloablMap()

    public fun actionGlobalBeerList(beerListUseCase: Int = 0): NavDirections =
        AppNavGraphDirections.actionGlobalBeerList(beerListUseCase)

    public fun actionGloablUserArea(): NavDirections = AppNavGraphDirections.actionGloablUserArea()
  }
}
