package repository

import constants.paginationLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.Restaurant
import models.RestaurantCollections
import models.RestaurantImpl
import utils.ScraperRequest
import utils.coroutines.AsyncTasksManager
import utils.extensions.getAListOfRestaurantsWhenGivenADivSelector
import java.net.URL

class RestaurantsRepoImpl(private val asyncTasksManager: AsyncTasksManager):AsyncTasksManager by asyncTasksManager,RestaurantsRepository {
    private val divCssSelector="area-places"
    private val destination= mutableListOf<Restaurant>()
    private  val acceptedTerms = listOf("outdoor-seating","cocktails","wine-selection","suitable-for-vegans-vegetarians")
    private val jsonInstance by lazy { Json{ignoreUnknownKeys=true} }


    @kotlinx.serialization.Serializable
    internal data class PaginatedRestaurantsWrapper(@SerialName("places")val restaurants:List<RestaurantImpl>)


    override suspend fun fetchAListOfRestaurantsWhenGivenACollection(restaurantsCollection: RestaurantCollections) = asyncTasksManager.performTaskAsynchronouslyAndAwaitForResult {
           when{
               restaurantsCollection.isCollectionPaginated->{
                   fetchRestaurantsBelongingToThisCollectionFromTheApi(restaurantsCollection.restaurantCollectionLink)
               }
               else->{
                   fetchRestaurantsBelongingToThisCollectionByScrapingTheWebsite(restaurantsCollection.restaurantCollectionLink)
               }
           }
       }

    private suspend fun fetchRestaurantsBelongingToThisCollectionByScrapingTheWebsite(collectionLink:String):List<Restaurant>{
        val scraperRequestInstance by lazy { ScraperRequest(urlToBeUsed = collectionLink) }
        return scraperRequestInstance.getAListOfRestaurantsWhenGivenADivSelector(destination = destination,divSelector =divCssSelector)
    }
    private fun fetchRestaurantsBelongingToThisCollectionFromTheApi(collectionLink: String):List<Restaurant>{
        val currentTerm=acceptedTerms.first { it in collectionLink }
        val paginationLink=paginationLink(currentTerm)
        return URL(paginationLink).openStream().bufferedReader().use {
          return@use  jsonInstance.decodeFromString<PaginatedRestaurantsWrapper>(it.readText()).restaurants
        }
    }
}