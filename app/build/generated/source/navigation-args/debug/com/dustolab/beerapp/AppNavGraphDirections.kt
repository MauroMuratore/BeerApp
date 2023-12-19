package com.dustolab.beerapp

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import kotlin.Int

public class AppNavGraphDirections private constructor() {
  private data class ActionGlobalBeerList(
    public val beerListUseCase: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_global_beer_list

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("beerListUseCase", this.beerListUseCase)
        return result
      }
  }

  public companion object {
    public fun actionGlobalHome(): NavDirections = ActionOnlyNavDirections(R.id.action_global_home)

    public fun actionGlobalSocial(): NavDirections =
        ActionOnlyNavDirections(R.id.action_global_social)

    public fun actionGloablMap(): NavDirections = ActionOnlyNavDirections(R.id.action_gloabl_map)

    public fun actionGlobalBeerList(beerListUseCase: Int = 0): NavDirections =
        ActionGlobalBeerList(beerListUseCase)

    public fun actionGloablUserArea(): NavDirections =
        ActionOnlyNavDirections(R.id.action_gloabl_user_area)
  }
}
