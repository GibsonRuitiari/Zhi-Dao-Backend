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

import models.RestaurantImpl
import utils.ScraperRequest
import utils.coroutines.AsyncTasksManager
import utils.extensions.getAListOfRestaurantsWhenGivenADivSelector

internal class CuisinesRepositoryImpl(private val asyncTasksManager: AsyncTasksManager) : CuisinesRepository, AsyncTasksManager by asyncTasksManager {
  override suspend fun getRestaurantsServingAParticularCuisinesWhenGivenACuisineLink(cuisineLink: String) = asyncTasksManager.performTaskAsynchronouslyAndAwaitForResult {
    val destination = mutableListOf<RestaurantImpl>()
    val request by lazy { ScraperRequest(urlToBeUsed = cuisineLink) }
    request.getAListOfRestaurantsWhenGivenADivSelector(destination = destination, divSelector = "archive-place")
  }
}
