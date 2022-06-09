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

import models.FeaturedRestaurantsHomePage
import models.HomePageRestaurantsImpl
import models.PopularCuisines
import models.scrapingResult.ScraperResult

interface HomePageRepository {
  suspend fun getAllFeaturedRestaurantsInHomePage(): ScraperResult<List<FeaturedRestaurantsHomePage>>
  suspend fun getAllPopularCuisinesInHomePage(): ScraperResult<List<PopularCuisines>>
  suspend fun getAllVeganRestaurantsInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
  suspend fun getAllRestaurantsThatAreGreatForOutdoorsInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
  suspend fun getAllRestaurantsThatAreGreatForWineLoversInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
  suspend fun getAllRestaurantsThatHaveGreatCityViewInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
  suspend fun getAllCafesGreatForInstagramInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
  suspend fun getAllPetFriendlyCafesInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
  suspend fun getAllCosyCafesSuitableToWorkFromInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
  suspend fun getAllCafesGreatForAfterWorkDrinksInHomePage(): ScraperResult<List<HomePageRestaurantsImpl>>
}
