package utils.extensions

import constants.*
import it.skrape.core.document
import it.skrape.core.htmlDocument
import it.skrape.fetcher.Request
import it.skrape.fetcher.Scraper
import it.skrape.fetcher.response
import it.skrape.selects.eachHref
import it.skrape.selects.eachSrc
import it.skrape.selects.eachText
import it.skrape.selects.html5.*
import models.Restaurant
import models.RestaurantImpl



private val restaurantDiningTypesList = mutableListOf<String>()
private val restaurantNamesList = mutableListOf<String>()
private val restaurantsPriceRatingList = mutableListOf<String>()

suspend fun Scraper<Request>.scrapeGeneralRestaurantsDetails(sectionCssSelector:String) = response {
    htmlDocument {
        section {
            withClass=sectionCssSelector
            // partial since it contains only three details:name,price rating and dining type
            val partialRestaurantsDetailsLists=div {
                withClass= restaurantsDetailsCssSelectorDiv    //"place-item"
                findAll {
                    val scrapedRestaurantsIterator = eachText.iterator()
                    scrapedRestaurantsIterator.forEach {
                        val (restaurantsNameRegexResult,restaurantPriceRatingRegexResult,restaurantDiningCategoryRegexResult)=findMatchingResultsFromRegularExpression(it)
                        val regexResultWrapper= RegexResultWrapper(restaurantsNameRegexResult,restaurantPriceRatingRegexResult, restaurantDiningCategoryRegexResult)
                        regexResultWrapper.checkIfRegexResultIsNullAndAddItToCollection()
                    }
                    return@findAll Triple(restaurantNamesList,restaurantsPriceRatingList,restaurantDiningTypesList)
                }
            }
            val restaurantsLinksList=a{
                withClass= restaurantsDetailsCssSelectorThumbnailLinks      //"entry-thumb"
                findAll { return@findAll eachHref }
            }
            val restaurantsThumbnailsList = img { findAll { return@findAll eachSrc }}
            val (restaurantsNames, restaurantsPriceRating, restaurantsDiningTypes) = partialRestaurantsDetailsLists
            return@section CollectionsContainer(restaurantsNames,restaurantsPriceRating,restaurantsDiningTypes,restaurantsThumbnailsList,restaurantsLinksList)
        }


    }
}
internal data class RegexResultWrapper(val nameRegexResult:String?,val priceRatingResult:String?,val diningCategoryResult:String?)
private fun RegexResultWrapper.checkIfRegexResultIsNullAndAddItToCollection(){
    nameRegexResult?.let {n-> restaurantNamesList.add(n) }
    priceRatingResult?.let {pr-> restaurantsPriceRatingList.add(pr) }
    diningCategoryResult?.let { cr -> restaurantDiningTypesList.add(cr) }
}
suspend fun Scraper<Request>.getAListOfRestaurantsWhenGivenADivSelector(divSelector:String,destination:MutableList<Restaurant>) = response {
       htmlDocument {
           div {
               withClass=divSelector
               div {
                   withClass = restaurantsDetailsCssSelectorDiv   //"place-item"
                   val restaurantsLinksAndImgs = a {
                       withClass = restaurantsDetailsCssSelectorThumbnailLinks   //"entry-thumb"
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
                       RestaurantImpl(restaurantName,restaurantThumbnailLink,restaurantLink,restaurantDiningType,restaurantPriceRating)
                   }.toList()
               }
           }
       }
   }
