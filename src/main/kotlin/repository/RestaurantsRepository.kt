package repository

import models.Restaurant
import models.RestaurantCollections
import models.scrapingResult.ScraperResult

interface RestaurantsRepository {
    suspend fun fetchAListOfRestaurantsWhenGivenACollection(restaurantsCollection:RestaurantCollections):ScraperResult<List<Restaurant>>
}