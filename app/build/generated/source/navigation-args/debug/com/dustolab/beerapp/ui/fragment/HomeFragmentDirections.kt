package com.dustolab.beerapp.ui.fragment

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.dustolab.beerapp.AppNavGraphDirections
import com.dustolab.beerapp.R
import kotlin.Int

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeToBeerList(
    public val beerListUseCase: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_home_to_beer_list

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("beerListUseCase", this.beerListUseCase)
        return result
      }
  }

  public companion object {
    public fun actionHomeToBeerList(beerListUseCase: Int = 0): NavDirections =
        ActionHomeToBeerList(beerListUseCase)

    public fun actionHomeToMap(): NavDirections = ActionOnlyNavDirections(R.id.action_home_to_map)

    public fun actionGlobalHome(): NavDirections = AppNavGraphDirections.actionGlobalHome()

    public fun actionGlobalSocial(): NavDirections = AppNavGraphDirections.actionGlobalSocial()

    public fun actionGloablMap(): NavDirections = AppNavGraphDirections.actionGloablMap()

    public fun actionGlobalBeerList(beerListUseCase: Int = 0): NavDirections =
        AppNavGraphDirections.actionGlobalBeerList(beerListUseCase)

    public fun actionGloablUserArea(): NavDirections = AppNavGraphDirections.actionGloablUserArea()
  }
}
