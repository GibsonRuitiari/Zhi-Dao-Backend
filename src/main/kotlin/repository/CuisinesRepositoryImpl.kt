package repository

import models.Restaurant
import utils.ScraperRequest
import utils.coroutines.AsyncTasksManager
import utils.extensions.getAListOfRestaurantsWhenGivenADivSelector

class CuisinesRepositoryImpl (private val asyncTasksManager: AsyncTasksManager):CuisinesRepository, AsyncTasksManager by asyncTasksManager {
    override suspend fun getRestaurantsServingAParticularCuisinesWhenGivenACuisineLink(cuisineLink: String)= asyncTasksManager.performTaskAsynchronouslyAndAwaitForResult {
        val destination = mutableListOf<Restaurant>()
        val request by lazy { ScraperRequest(urlToBeUsed = cuisineLink) }
        request.getAListOfRestaurantsWhenGivenADivSelector(destination = destination,divSelector = "archive-place")
    }


}

