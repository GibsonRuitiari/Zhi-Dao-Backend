package main

import main.entrypoint.RestaurantsManager

object Restaurants{
    operator fun invoke() =  RestaurantsManager.instance
}