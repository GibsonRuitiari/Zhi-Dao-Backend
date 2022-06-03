package repository

import models.Restaurant
import models.scrapingResult.ScraperResult

interface CuisinesRepository {
    suspend fun getRestaurantsServingAParticularCuisinesWhenGivenACuisineLink(cuisineLink:String):ScraperResult<List<Restaurant>>
}