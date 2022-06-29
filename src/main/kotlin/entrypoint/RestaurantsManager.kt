/*
 * Copyright 2022 Gibson Ruitiari.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package main.entrypoint

import models.AllCuisines
import models.RestaurantCollections
import repository.CuisinesRepository
import repository.CuisinesRepositoryImpl
import repository.HomePageRepository
import repository.HomePageRepositoryImpl
import repository.RestaurantsDetailsRepository
import repository.RestaurantsDetailsRepositoryImpl
import repository.RestaurantsRepoImpl
import repository.RestaurantsRepository
import utils.coroutines.DefaultAsyncTasksHandler

class RestaurantsManager {
  private val asyncTaskManager by lazy { DefaultAsyncTasksHandler() }
  private val cuisinesRepository: CuisinesRepository by lazy { CuisinesRepositoryImpl(asyncTaskManager) }
  private val homePageRepository: HomePageRepository by lazy { HomePageRepositoryImpl(asyncTaskManager) }
  private val restaurantsDetailsRepository: RestaurantsDetailsRepository by lazy { RestaurantsDetailsRepositoryImpl(asyncTaskManager) }
  private val restaurantsRepository: RestaurantsRepository by lazy { RestaurantsRepoImpl(asyncTaskManager) }

  fun getAllCuisinesServedByEveryRestaurant() = AllCuisines.listOfAllCuisines()
  suspend fun getAllFeaturedRestaurants() = homePageRepository.getAllFeaturedRestaurantsInHomePage()
  suspend fun getAllPopularCuisines() = homePageRepository.getAllPopularCuisinesInHomePage()
  suspend fun getAllVeganRestaurants() = homePageRepository.getAllVeganRestaurantsInHomePage()
  suspend fun getAllOutdoorRestaurants() = homePageRepository.getAllRestaurantsThatAreGreatForOutdoorsInHomePage()
  suspend fun getAllWineRestaurants() = homePageRepository.getAllRestaurantsThatAreGreatForWineLoversInHomePage()
  suspend fun getAllCityViewRestaurants() = homePageRepository.getAllRestaurantsThatHaveGreatCityViewInHomePage()
  suspend fun getAllPetFriendlyRestaurants() = homePageRepository.getAllPetFriendlyCafesInHomePage()
  suspend fun getAllCocktailsRestaurants() = homePageRepository.getAllCafesGreatForAfterWorkDrinksInHomePage()
  suspend fun getAllGramWorthyRestaurants() = homePageRepository.getAllCafesGreatForInstagramInHomePage()
  suspend fun getCosyRestaurants() = homePageRepository.getAllCosyCafesSuitableToWorkFromInHomePage()

  suspend fun getRestaurantsByCuisine(cuisineLink: String) = cuisinesRepository.getRestaurantsServingAParticularCuisinesWhenGivenACuisineLink(cuisineLink)
  suspend fun getRestaurantsByCollection(collection: RestaurantCollections) = restaurantsRepository.fetchAListOfRestaurantsWhenGivenACollection(collection)
  suspend fun getRestaurantsDetails(restaurantLink: String) = restaurantsDetailsRepository.getAllRestaurantDetails(restaurantLink)
  fun cleanUpCoroutineDeferredObjects() = asyncTaskManager.cleanUpResources()

  companion object {
    val instance by lazy { RestaurantsManager() }
  }
}
