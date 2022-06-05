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

import constants.afterWorkSectionCssSelector
import constants.afterWorkSubtitle
import constants.afterWorkTitle
import constants.cityViewSubtitle
import constants.cityViewTitle
import constants.cosyCafeSubtitle
import constants.cosyCafeTitle
import constants.cosyCafesSectionCssSelector
import constants.cuisinesDetailsSectionCssSelector
import constants.cuisinesDivCssClassSelector
import constants.cuisinesImgCssSelector
import constants.cuisinesTitleCssSelector
import constants.featuredRestaurantsSectionCssSelector
import constants.gramSubtitle
import constants.gramTitle
import constants.greatForGramSectionCssSelector
import constants.greatViewCitySectionCssSelector
import constants.outdoorSectionCssSelector
import constants.outdoorSubtitle
import constants.outdoorTitle
import constants.petFriendlySectionCssSelector
import constants.petFriendlySubtitle
import constants.petFriendlyTitle
import constants.vegansHomePageCssSelector
import constants.vegansSubtitle
import constants.vegansTitle
import constants.wineLoversSectionCssSelector
import constants.wineSubtitle
import constants.wineTitle
import it.skrape.core.document
import it.skrape.fetcher.response
import it.skrape.selects.CssSelector
import it.skrape.selects.eachHref
import it.skrape.selects.eachSrc
import it.skrape.selects.eachText
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.h3
import it.skrape.selects.html5.img
import it.skrape.selects.html5.section
import models.AfterWorkDrinksHeader
import models.BestForVegansHeader
import models.BestForWineLoversHeader
import models.CosyCafesToWorkFromHeader
import models.FeaturedRestaurants
import models.FeaturedRestaurantsHomePage
import models.ForTheGramHeaderHeader
import models.GreatOutdoorsHeader
import models.HomePageParam
import models.HomePageRestaurants
import models.HomePageRestaurantsImpl
import models.IncredibleCityViewsHeader
import models.PetFriendlyHeader
import models.PopularCuisines
import models.RestaurantCollections
import models.scrapingResult.ScraperResult
import utils.ScraperRequest
import utils.coroutines.AsyncTasksManager
import utils.extensions.collectionsContainerIterator
import utils.extensions.map
import utils.extensions.scrapeGeneralRestaurantsDetails

class HomePageRepositoryImpl constructor(private val asyncTasksManager: AsyncTasksManager) : AsyncTasksManager by asyncTasksManager, HomePageRepository {
  private val scraperRequestInstance by lazy { ScraperRequest() }

  private val featuredRestaurantsInHomePageList = mutableListOf<FeaturedRestaurants>()
  private val cuisines = mutableListOf<PopularCuisines>()

  override suspend fun getAllFeaturedRestaurantsInHomePage(): ScraperResult<List<FeaturedRestaurants>> = asyncTasksManager.performTaskAsynchronouslyAndAwaitForResult {
    val restaurantsDetailsCollections = scraperRequestInstance.scrapeGeneralRestaurantsDetails(featuredRestaurantsSectionCssSelector)
    restaurantsDetailsCollections.collectionsContainerIterator().map(featuredRestaurantsInHomePageList) {
      val (restaurantName, restaurantPriceRating, restaurantDiningType, restaurantThumbnailLink, restaurantLink) = it
      FeaturedRestaurantsHomePage(restaurantName, restaurantThumbnailLink, restaurantLink, restaurantDiningType, restaurantPriceRating, true)
    }
  }

  override suspend fun getAllPopularCuisinesInHomePage(): ScraperResult<List<PopularCuisines>> = asyncTasksManager.performTaskAsynchronouslyAndAwaitForResult {
    scraperRequestInstance.response {
      document.section {
        withClass = cuisinesDetailsSectionCssSelector // "elementor-element-1521c61"
        return@section div {
          withClass = cuisinesDivCssClassSelector // "swiper-wrapper"
          val cuisineNames = givenACssSelectorGenerateCuisinesTitles(this)
          val cuisinesThumbnailLinks = givenACssSelectorGenerateCuisinesThumbnailLinks(this)
          val cuisinesLinks = givenACssSelectorGenerateCuisinesLinks(this)
          val cuisinesIterator = Triple(cuisineNames, cuisinesThumbnailLinks, cuisinesLinks)
          cuisinesIterator.collectionsContainerIterator().map(cuisines) {
            val (name, thumbnailLink, link) = it
            PopularCuisines(name, thumbnailLink, link)
          }
        }
      }
    }.toList()
  }

  override suspend fun getAllVeganRestaurantsInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    // val vegansRestaurantsCssSelector ="elementor-element-9da732d"
    val veganRestaurantsList = mutableListOf<HomePageRestaurants>()
    val veganTitleAndSubtitle = BestForVegansHeader(vegansTitle, vegansSubtitle)
    val homePageParam = HomePageParam(headerTitleAndSubtitle = veganTitleAndSubtitle, sectionCssSelector = vegansHomePageCssSelector, destinationCollection = veganRestaurantsList, collections = RestaurantCollections.Vegans)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }

  override suspend fun getAllRestaurantsThatAreGreatForOutdoorsInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    // val outdoorRestaurantsCssSelector= outdoorSectionCssSelector   //"elementor-element-792909a"
    val outdoorRestaurantsTitleAndSubtitle = GreatOutdoorsHeader(outdoorTitle, outdoorSubtitle)
    val outdoorRestaurantsList = mutableListOf<HomePageRestaurants>()
    val homePageParam = HomePageParam(headerTitleAndSubtitle = outdoorRestaurantsTitleAndSubtitle, sectionCssSelector = outdoorSectionCssSelector, destinationCollection = outdoorRestaurantsList, collections = RestaurantCollections.OutdoorSeating)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }

  override suspend fun getAllRestaurantsThatAreGreatForWineLoversInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    // val wineLoversRestaurantsCssSelector ="elementor-element-933d675"
    val wineLoversTitleAndSubtitle = BestForWineLoversHeader(wineTitle, wineSubtitle)
    val wineLoversRestaurantsList = mutableListOf<HomePageRestaurants>()
    val homePageParam = HomePageParam(headerTitleAndSubtitle = wineLoversTitleAndSubtitle, sectionCssSelector = wineLoversSectionCssSelector, destinationCollection = wineLoversRestaurantsList, collections = RestaurantCollections.WineLovers)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }

  override suspend fun getAllRestaurantsThatHaveGreatCityViewInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    // val restaurantsCssSelector ="elementor-element-8b80d60"
    val titleAndSubtitle = IncredibleCityViewsHeader(cityViewTitle, cityViewSubtitle)
    val restaurantsList = mutableListOf<HomePageRestaurants>()
    val homePageParam = HomePageParam(headerTitleAndSubtitle = titleAndSubtitle, sectionCssSelector = greatViewCitySectionCssSelector, destinationCollection = restaurantsList, collections = RestaurantCollections.NairobiView)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }

  override suspend fun getAllCafesGreatForInstagramInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    //  val restaurantsCssSelector ="elementor-element-42e8064"
    val titleAndSubtitle = ForTheGramHeaderHeader(gramTitle, gramSubtitle)
    val restaurantsList = mutableListOf<HomePageRestaurants>()
    val homePageParam = HomePageParam(headerTitleAndSubtitle = titleAndSubtitle, sectionCssSelector = greatForGramSectionCssSelector, destinationCollection = restaurantsList, collections = RestaurantCollections.GramWorthy)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }

  override suspend fun getAllPetFriendlyCafesInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    //  val restaurantsCssSelector ="elementor-element-ca71f75"
    val titleAndSubtitle = PetFriendlyHeader(petFriendlyTitle, petFriendlySubtitle)
    val restaurantsList = mutableListOf<HomePageRestaurants>()
    val homePageParam = HomePageParam(headerTitleAndSubtitle = titleAndSubtitle, sectionCssSelector = petFriendlySectionCssSelector, destinationCollection = restaurantsList, collections = RestaurantCollections.PetFriendly)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }

  override suspend fun getAllCosyCafesSuitableToWorkFromInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    //   val restaurantsCssSelector ="elementor-element-7227130"
    val titleAndSubtitle = CosyCafesToWorkFromHeader(cosyCafeTitle, cosyCafeSubtitle)
    val restaurantsList = mutableListOf<HomePageRestaurants>()
    val homePageParam = HomePageParam(headerTitleAndSubtitle = titleAndSubtitle, sectionCssSelector = cosyCafesSectionCssSelector, destinationCollection = restaurantsList, collections = null)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }

  override suspend fun getAllCafesGreatForAfterWorkDrinksInHomePage(): ScraperResult<List<HomePageRestaurants>> {
    // val restaurantsCssSelector ="elementor-element-eba6db8"
    val titleAndSubtitle = AfterWorkDrinksHeader(afterWorkTitle, afterWorkSubtitle)
    val restaurantsList = mutableListOf<HomePageRestaurants>()
    val homePageParam = HomePageParam(headerTitleAndSubtitle = titleAndSubtitle, sectionCssSelector = afterWorkSectionCssSelector, destinationCollection = restaurantsList, collections = RestaurantCollections.Cocktails)
    return returnListOfHomePageRestaurantsGivenHomePageParam(homePageParam)
  }
  private fun givenACssSelectorGenerateCuisinesThumbnailLinks(selector: CssSelector) = selector.img {
    findAll { return@findAll eachSrc }
  }
  private fun givenACssSelectorGenerateCuisinesLinks(selector: CssSelector) = selector.a {
    withClass = cuisinesImgCssSelector // "slide-wrapper"
    findAll { return@findAll eachHref }
  }
  private fun givenACssSelectorGenerateCuisinesTitles(selector: CssSelector) = selector.h3 {
    withClass = cuisinesTitleCssSelector // "title"
    findAll { return@findAll eachText }
  }
  private suspend fun returnListOfHomePageRestaurantsGivenHomePageParam(param0: HomePageParam) = asyncTasksManager.performTaskAsynchronouslyAndAwaitForResult {
    val (headerTitleAndSubtitle, cssSelector, collection, destination) = param0
    val restaurantsDetailsCollections = scraperRequestInstance.scrapeGeneralRestaurantsDetails(cssSelector)
    restaurantsDetailsCollections.collectionsContainerIterator().map(destination = destination) {
      val (restaurantName, restaurantPriceRating, restaurantDiningType, restaurantThumbnailLink, restaurantLink) = it
      HomePageRestaurantsImpl(titleAndSubtitle = headerTitleAndSubtitle, restaurantName = restaurantName, restaurantThumbnailLink = restaurantThumbnailLink, restaurantLink = restaurantLink, restaurantPriceRating = restaurantPriceRating, restaurantDiningCategory = restaurantDiningType, collection = collection)
    }.toList()
  }
}
