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

import com.google.common.truth.Truth.assertThat
import models.RestaurantCollections
import models.RestaurantImpl
import models.scrapingResult.ScrapingError
import models.scrapingResult.ScrapingSuccess
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import utils.coroutines.DefaultAsyncTasksHandler
import java.net.UnknownHostException

class RepositoryImplTests {
  private var asyncTaskManagerInstance: DefaultAsyncTasksHandler? = null
  private var cuisinesRepository: CuisinesRepository ? = null
  private var homePageRepository: HomePageRepository ? = null
  private var restaurantsRepository: RestaurantsRepository? = null
  private val testCuisinesLink = "https://eatout.co.ke/cuisine/Barbeque/?city=nairobi"
  private val beerAndBones = RestaurantImpl("Beer &amp; Bones &#8211; NSK", restaurantDiningCategory = "Food Court", restaurantPriceRating = "$$$", restaurantLink = "https://eatout.co.ke/nairobi/beer-bones-nsk/", restaurantThumbnailLink = "https://eatout.co.ke/wp-content/uploads/2021/07/Beer-Bones-Logo-Subbrands-540x480.png")
  private val grillShackRestaurant = RestaurantImpl("Grill Shack", restaurantDiningCategory = "Casual Dining", restaurantPriceRating = "$$", restaurantThumbnailLink = "https://eatout.co.ke/wp-content/uploads/2021/04/1The-Grill-Shack-540x480.jpg", restaurantLink = "https://eatout.co.ke/restaurant/grill-shack/")
  private val petFriendlyRestaurantCollection = RestaurantCollections.PetFriendly
  @BeforeAll
  fun setUp() {
    asyncTaskManagerInstance = DefaultAsyncTasksHandler()
    cuisinesRepository = CuisinesRepositoryImpl(asyncTaskManagerInstance!!)
    homePageRepository = HomePageRepositoryImpl(asyncTaskManagerInstance!!)
    restaurantsRepository = RestaurantsRepoImpl(asyncTaskManagerInstance!!)
  }

  @Test
  suspend fun `testHomePageRepository's Functions And Ensure They Return A Result Wrapper in ScrapperResults`(){
    homePageRepository?.let {
      when (val results = it.getAllFeaturedRestaurantsInHomePage()) {
        is ScrapingSuccess -> {
          assertThat(results.data).isNotNull()
        }
        is ScrapingError -> {
          assertThat(results.errorMessage).isNotNull()
          results.exception.assertThatWhenAnExceptionOccursTheExpectedErrorMessageIsReturned(results.errorMessage!!)
        }
      }
      when(val results= it.getAllPopularCuisinesInHomePage()){
        is ScrapingSuccess -> {
          assertThat(results.data).isNotNull()
        }
        is ScrapingError -> {
          assertThat(results.errorMessage).isNotNull()
          results.exception.assertThatWhenAnExceptionOccursTheExpectedErrorMessageIsReturned(results.errorMessage!!)
        }
      }
      when(val results= it.getAllCafesGreatForInstagramInHomePage()){
        is ScrapingSuccess -> {
          assertThat(results.data).isNotNull()
        }
        is ScrapingError -> {
          assertThat(results.errorMessage).isNotNull()
          results.exception.assertThatWhenAnExceptionOccursTheExpectedErrorMessageIsReturned(results.errorMessage!!)
        }
      }
    }
  }

  @Test
  suspend fun `test CuisinesRepository And Ensure It Returns A ResultWrapped In ScraperResults`() {

    cuisinesRepository?.let {
      when (val results = it.getRestaurantsServingAParticularCuisinesWhenGivenACuisineLink(testCuisinesLink)) {
        is ScrapingSuccess -> {
          assertThat(results.data).isNotNull()
          assertThat(results.data).hasSize(2)
          assertThat(results.data).containsAtLeast(grillShackRestaurant, beerAndBones)
        }
        is ScrapingError -> {
          assertThat(results.errorMessage).isNotNull()
          results.exception.assertThatWhenAnExceptionOccursTheExpectedErrorMessageIsReturned(results.errorMessage!!)
        }
      }
    }

  }
  @Test
  suspend fun `test RestaurantsRepository And Ensure It's Functions Return A Result Wrapped in Scraper Result`(){
    restaurantsRepository?.let {
      when (val results = it.fetchAListOfRestaurantsWhenGivenACollection(petFriendlyRestaurantCollection)) {
        is ScrapingSuccess -> {
          assertThat(results.data).isNotNull()
          assertThat(results.data).containsNoDuplicates()
          assertThat(results.data).hasSize(7)
        }
        is ScrapingError -> {
          assertThat(results.errorMessage).isNotNull()
          results.exception.assertThatWhenAnExceptionOccursTheExpectedErrorMessageIsReturned(results.errorMessage!!)
        }
      }
    }
  }
  private fun Throwable.assertThatWhenAnExceptionOccursTheExpectedErrorMessageIsReturned(errorMessage: String) =
    when (this) {
      is UnknownHostException -> {
        assertThat(errorMessage).contains("Scraping failed due to client's internet connection")
      }
      else -> {
        assertThat(errorMessage).contains("An unknown error occurred. Please try again later.")
      }
    }
  @AfterAll
  fun tearDown() {
    if (asyncTaskManagerInstance != null) {
      asyncTaskManagerInstance?.cleanUpResources()
      asyncTaskManagerInstance = null
    }
    cuisinesRepository = null
    homePageRepository = null
    restaurantsRepository = null
  }
}
