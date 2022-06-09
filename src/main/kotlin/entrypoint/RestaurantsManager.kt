package main.entrypoint

import models.AllCuisines
import models.RestaurantCollections
import repository.CuisinesRepository
import repository.HomePageRepository
import repository.RestaurantsRepository
import repository.RestaurantsDetailsRepository
import repository.CuisinesRepositoryImpl
import repository.HomePageRepositoryImpl
import repository.RestaurantsDetailsRepositoryImpl
import repository.RestaurantsRepoImpl
import utils.coroutines.DefaultAsyncTasksHandler

class RestaurantsManager{
    private val asyncTaskManager by lazy { DefaultAsyncTasksHandler() }
    private val cuisinesRepository: CuisinesRepository by lazy { CuisinesRepositoryImpl(asyncTaskManager) }
    private val homePageRepository: HomePageRepository by lazy { HomePageRepositoryImpl(asyncTaskManager) }
    private val restaurantsDetailsRepository: RestaurantsDetailsRepository by lazy { RestaurantsDetailsRepositoryImpl(asyncTaskManager) }
    private val restaurantsRepository: RestaurantsRepository by lazy { RestaurantsRepoImpl(asyncTaskManager) }

    fun getAllCuisinesServedByEveryRestaurant() =  AllCuisines.listOfAllCuisines()
    suspend fun getAllFeaturedRestaurants() = homePageRepository.getAllFeaturedRestaurantsInHomePage()
    suspend fun getAllPopularCuisines() = homePageRepository.getAllPopularCuisinesInHomePage()
    suspend fun getAllVeganRestaurants() = homePageRepository.getAllVeganRestaurantsInHomePage()
    suspend fun getAllOutdoorRestaurants() = homePageRepository.getAllRestaurantsThatAreGreatForOutdoorsInHomePage()
    suspend fun getAllWineRestaurants() = homePageRepository.getAllRestaurantsThatAreGreatForWineLoversInHomePage()
    suspend fun getAllCityViewRestaurants()=homePageRepository.getAllRestaurantsThatHaveGreatCityViewInHomePage()
    suspend fun getAllPetFriendlyRestaurants() = homePageRepository.getAllPetFriendlyCafesInHomePage()
    suspend fun getAllCocktailsRestaurants() = homePageRepository.getAllCafesGreatForAfterWorkDrinksInHomePage()
    suspend fun getAllGramWorthyRestaurants() = homePageRepository.getAllCafesGreatForInstagramInHomePage()
    suspend fun getCosyRestaurants() = homePageRepository.getAllCosyCafesSuitableToWorkFromInHomePage()

    suspend fun getRestaurantsByCuisine(cuisineLink:String) = cuisinesRepository.getRestaurantsServingAParticularCuisinesWhenGivenACuisineLink(cuisineLink)
    suspend fun getRestaurantsByCollection(collection: RestaurantCollections) = restaurantsRepository.fetchAListOfRestaurantsWhenGivenACollection(collection)
    suspend fun getRestaurantsDetails(restaurantLink:String) = restaurantsDetailsRepository.getAllRestaurantDetails(restaurantLink)
    fun cleanUpCoroutineDeferredObjects() = asyncTaskManager.cleanUpResources()

    companion object{
        val instance by lazy {RestaurantsManager()}
    }
}