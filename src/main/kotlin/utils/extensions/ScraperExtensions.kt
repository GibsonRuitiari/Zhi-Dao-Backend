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
package utils.extensions

import constants.restaurantsDetailsCssSelectorDiv
import constants.restaurantsDetailsCssSelectorThumbnailLinks
import constants.similarRestaurantsDiningCategoriesDiv
import constants.similarRestaurantsH3CssSelector
import constants.similarRestaurantsPriceTagsDivCssSelector
import it.skrape.core.htmlDocument
import it.skrape.fetcher.Request
import it.skrape.fetcher.Scraper
import it.skrape.fetcher.response
import it.skrape.selects.eachHref
import it.skrape.selects.eachSrc
import it.skrape.selects.eachText
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.h3
import it.skrape.selects.html5.img
import it.skrape.selects.html5.section
import models.Restaurant
import models.RestaurantImpl

private val restaurantDiningTypesList = mutableListOf<String>()
private val restaurantNamesList = mutableListOf<String>()
private val restaurantsPriceRatingList = mutableListOf<String>()

suspend fun Scraper<Request>.scrapeGeneralRestaurantsDetails(sectionCssSelector: String) = response {
  htmlDocument {
    section {
      withClass = sectionCssSelector
      // partial since it contains only three details:name,price rating and dining type
      val partialRestaurantsDetailsLists = div {
        withClass = restaurantsDetailsCssSelectorDiv // "place-item"
        findAll {
          val scrapedRestaurantsIterator = eachText.iterator()
          scrapedRestaurantsIterator.forEach {
            val (restaurantsNameRegexResult, restaurantPriceRatingRegexResult, restaurantDiningCategoryRegexResult) = findMatchingResultsFromRegularExpression(it)
            val regexResultWrapper = RegexResultWrapper(restaurantsNameRegexResult, restaurantPriceRatingRegexResult, restaurantDiningCategoryRegexResult)
            regexResultWrapper.checkIfRegexResultIsNullAndAddItToCollection()
          }
          return@findAll Triple(restaurantNamesList.toList(), restaurantsPriceRatingList.toList(), restaurantDiningTypesList.toList())
        }
      }
      val restaurantsLinksList = a {
        withClass = restaurantsDetailsCssSelectorThumbnailLinks // "entry-thumb"
        findAll { return@findAll eachHref }
      }
      val restaurantsThumbnailsList = img { findAll { return@findAll eachSrc } }
      val (restaurantsNames, restaurantsPriceRating, restaurantsDiningTypes) = partialRestaurantsDetailsLists
      return@section CollectionsContainer(restaurantsNames, restaurantsPriceRating, restaurantsDiningTypes, restaurantsThumbnailsList, restaurantsLinksList)
    }
  }
}
internal data class RegexResultWrapper(val nameRegexResult: String?, val priceRatingResult: String?, val diningCategoryResult: String?)
private fun RegexResultWrapper.checkIfRegexResultIsNullAndAddItToCollection() {
  nameRegexResult?.let { n -> restaurantNamesList.add(n) }
  priceRatingResult?.let { pr -> restaurantsPriceRatingList.add(pr) }
  diningCategoryResult?.let { cr -> restaurantDiningTypesList.add(cr) }
}
suspend fun Scraper<Request>.getAListOfRestaurantsWhenGivenADivSelector(divSelector: String, destination: MutableList<Restaurant>) = response {
  htmlDocument {
    div {
      withClass = divSelector
      div {
        withClass = restaurantsDetailsCssSelectorDiv // "place-item"
        val restaurantsLinksAndImgs = a {
          withClass = restaurantsDetailsCssSelectorThumbnailLinks // "entry-thumb"
          val restaurantsLinks = findAll {
            eachHref
          }
          val restaurantsImages = img {
            findAll {
              eachSrc
            }
          }
          restaurantsLinks to restaurantsImages
        }

        // title
        val placeTitle = h3 {
          withClass = similarRestaurantsH3CssSelector
          findAll {
            eachText
          }
        }
        //
        val diningPrice = div {
          withClass = similarRestaurantsPriceTagsDivCssSelector
          findAll {
            eachText
          }
        }
        // place term
        val diningCategories = div {
          withClass = similarRestaurantsDiningCategoriesDiv
          findAll {
            eachText
          }
        }

        val (restaurantLinks, restaurantsImgLinks) = restaurantsLinksAndImgs
        val collectionContainer = CollectionsContainer(
          placeTitle,
          diningPrice,
          diningCategories,
          restaurantsImgLinks,
          restaurantLinks
        )
        collectionContainer.collectionsContainerIterator().map(destination) {
          val (restaurantName, restaurantPriceRating, restaurantDiningType, restaurantThumbnailLink, restaurantLink) = it
          RestaurantImpl(restaurantName, restaurantThumbnailLink, restaurantLink, restaurantDiningType, restaurantPriceRating)
        }.toList()
      }
    }
  }
}
