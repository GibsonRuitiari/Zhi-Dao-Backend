package repository

import models.*
import models.scrapingResult.ScraperResult

interface HomePageRepository {
    suspend fun getAllFeaturedRestaurantsInHomePage():ScraperResult<List<FeaturedRestaurants>>
    suspend fun getAllPopularCuisinesInHomePage():ScraperResult<List<PopularCuisines>>
    suspend fun getAllVeganRestaurantsInHomePage():ScraperResult<List<HomePageRestaurants>>
    suspend fun getAllRestaurantsThatAreGreatForOutdoorsInHomePage():ScraperResult<List<HomePageRestaurants>>
    suspend fun getAllRestaurantsThatAreGreatForWineLoversInHomePage():ScraperResult<List<HomePageRestaurants>>
    suspend fun getAllRestaurantsThatHaveGreatCityViewInHomePage():ScraperResult<List<HomePageRestaurants>>
    suspend fun getAllCafesGreatForInstagramInHomePage():ScraperResult<List<HomePageRestaurants>>
    suspend fun getAllPetFriendlyCafesInHomePage():ScraperResult<List<HomePageRestaurants>>
    suspend fun getAllCosyCafesSuitableToWorkFromInHomePage():ScraperResult<List<HomePageRestaurants>>
    suspend fun getAllCafesGreatForAfterWorkDrinksInHomePage():ScraperResult<List<HomePageRestaurants>>
}