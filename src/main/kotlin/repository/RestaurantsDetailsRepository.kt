package repository

import models.RestaurantDetails

interface RestaurantsDetailsRepository {
    suspend fun getAllRestaurantDetails(restaurantLink:String):RestaurantDetails
}