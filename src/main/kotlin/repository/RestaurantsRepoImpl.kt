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
package repository

import constants.paginationLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.RestaurantCollections
import models.RestaurantImpl
import utils.ScraperRequest
import utils.coroutines.AsyncTasksManager
import utils.extensions.getAListOfRestaurantsWhenGivenADivSelector
import java.net.URL

internal class RestaurantsRepoImpl(private val asyncTasksManager: AsyncTasksManager) : AsyncTasksManager by asyncTasksManager, RestaurantsRepository {
  private val divCssSelector = "area-places"
  private val destination = mutableListOf<RestaurantImpl>()
  private val acceptedTerms = listOf("outdoor-seating", "cocktails", "wine-selection", "suitable-for-vegans-vegetarians")
  private val jsonInstance by lazy { Json { ignoreUnknownKeys = true } }

  @kotlinx.serialization.Serializable
  internal data class PaginatedRestaurantsWrapper(@SerialName("places")val restaurants: List<RestaurantImpl>)

  override suspend fun fetchAListOfRestaurantsWhenGivenACollection(restaurantsCollection: RestaurantCollections) = asyncTasksManager.performTaskAsynchronouslyAndAwaitForResult {
    when {
      restaurantsCollection.isCollectionPaginated -> {
        fetchRestaurantsBelongingToThisCollectionFromTheApi(restaurantsCollection.restaurantCollectionLink)
      }
      else -> {
        fetchRestaurantsBelongingToThisCollectionByScrapingTheWebsite(restaurantsCollection.restaurantCollectionLink)
      }
    }
  }

  private suspend fun fetchRestaurantsBelongingToThisCollectionByScrapingTheWebsite(collectionLink: String): List<RestaurantImpl> {
    val scraperRequestInstance by lazy { ScraperRequest(urlToBeUsed = collectionLink) }
    return scraperRequestInstance.getAListOfRestaurantsWhenGivenADivSelector(destination = destination, divSelector = divCssSelector)
  }
  private fun fetchRestaurantsBelongingToThisCollectionFromTheApi(collectionLink: String): List<RestaurantImpl> {
    val currentTerm = acceptedTerms.first { it in collectionLink }
    val paginationLink = paginationLink(currentTerm)
    return URL(paginationLink).openStream().bufferedReader().use {
      return@use jsonInstance.decodeFromString<PaginatedRestaurantsWrapper>(it.readText()).restaurants
    }
  }
}
