package repository

import constants.*
import it.skrape.core.htmlDocument
import it.skrape.fetcher.response
import it.skrape.selects.*
import it.skrape.selects.html5.*
import models.*
import utils.ScraperRequest
import utils.extensions.CollectionsContainer
import utils.extensions.collectionsContainerIterator
import utils.extensions.map

class RestaurantsDetailsRepositoryImpl: RestaurantsDetailsRepository {
    private val similarRestaurantsList = mutableListOf<Restaurant>()
    private fun generateRestaurantSlideShowPicturesFromDocElement(docElement:Doc) = docElement.div {
            withClass= restaurantsSlideShowPicturesCssSelector   //"margin-0"
            img {
                findAll {
                    return@findAll eachSrc
                }
            }
        }

    private fun generateRestaurantBasicAmenitiesFromDocElement(docElement:Doc) = docElement.ul {
            withClass= restaurantsBasicAmenitiesCssSelector  //"show-grid"
            val amenitiesThumbnailLinks=img {
                findAll {
                    return@findAll eachSrc
                }
            }
            val amenitiesText=findAll {
                return@findAll eachText
            }
            val zippedAmenitiesTextAndThumbnails=amenitiesText zip amenitiesThumbnailLinks
            return@ul  zippedAmenitiesTextAndThumbnails.map {
                val (amenityName, amenityThumbnailLink) = it
                return@map RestaurantAmenitiesImpl(amenityName,amenityThumbnailLink)
            }
        }

    private fun generateRestaurantDescriptionFromDocElement(docElement: Doc) = docElement.div {
            withClass= restaurantsDescriptionDivCssSelector //"entry-visibility"
            p{
                findAll {
                    return@findAll  eachText
                }
            }
        }.first()

    private fun generateRestaurantMenuItemsFromDocElement(docElement: Doc) = docElement.div {
            withClass= restaurantMenuItemsDivCssSelector //"modula-items"
            val resizedMenuItemsImageLinks=a{
                withAttributeKey= restaurantMenuItemsACssSelector  //"data-image-id"
                findAll {
                    return@findAll eachHref
                }
            }
            val originalMenuItemsImageLinks=img{
                withClass= restaurantMenuItemsImgCssSelector  //"pic"
                findAll {
                    return@findAll eachSrc }
            }
            val zippedMenuItems= resizedMenuItemsImageLinks zip originalMenuItemsImageLinks
            return@div zippedMenuItems.map {
                val (resizedMenuItemImageLink, originalMenuItemImageLink) = it
                return@map RestaurantMenuItemsImpl(resizedMenuItemImage = resizedMenuItemImageLink,
                    originalMenuItemImage = originalMenuItemImageLink)
            }
        }
    private fun generateRestaurantContactDetailsFromDocElement(docElement: Doc) = docElement.div {
        withClass= restaurantContactDetailsDivCssSelector //"entry-detail"
        a{
            withAttribute= restaurantContactDetailAKeyAttributeSelector to restaurantContactDetailAValueAttributeSelector   //"target" to "_blank"
            findAll {
                val links= eachHref
                val texts= eachText
                val combinedContactDetailsLinksAndTexts=links zip texts
                return@findAll combinedContactDetailsLinksAndTexts.map {
                    RestaurantContactsImpl(contactLabel = it.second, contact = it.second)
                }
            }
        }
    }
    private fun generateRestaurantOpeningHoursFromDocElement(docElement: Doc) = docElement.div{
        withClass= restaurantOpeningHoursDivCssSelector  //"entry-detail"
        span {
            withClass= restaurantOpeningHoursSpanCssSelector  //"open-time"
            findAll {
                return@findAll eachText
            }
        }
    }
    private fun generateListOfSimilarRestaurantsFromDocElement(docElement: Doc) =  docElement.div {
        withClass= similarRestaurantsDivCssSelector  //"golo-slick-carousel"
        withAttributeKey= similarRestaurantsDivAttributeKey //"data-slick"
        // restaurants links and images
        val pairOfRestaurantLinksAndThumbnailImages= a{
            withClass= similarRestaurantsACssSelector  //"entry-thumb"
            val restaurantImageLinks= img {
                findAll {
                    return@findAll eachSrc
                }
            }
            val restaurantLinks= findAll{
                return@findAll eachHref
            }
            return@a restaurantLinks  to restaurantImageLinks
        }
        val similarRestaurantsDiningCategories=div {
            withClass= similarRestaurantsDiningCategoriesDiv //"place-term"
            findAll {
                return@findAll eachText
            }
        }
        // title of the place
        val similarRestaurantsTitles= h3 {
            withClass= similarRestaurantsH3CssSelector  //"place-title"
            findAll {
                return@findAll eachText
            }
        }
        // place price
        val similarRestaurantsPriceTags= div {
            withClass= similarRestaurantsPriceTagsDivCssSelector  //"place-price"
            findAll{
                eachText
            }
        }
        val (similarRestaurantLinks, similarRestaurantThumbnailLinks) = pairOfRestaurantLinksAndThumbnailImages
        val similarRestaurantsContainer = CollectionsContainer(similarRestaurantsTitles,similarRestaurantsPriceTags,similarRestaurantsDiningCategories,similarRestaurantLinks,similarRestaurantThumbnailLinks)
        return@div  generateListOfRestaurantsFromCollectionsContainer(similarRestaurantsContainer)

    }
    private fun generateListOfRestaurantsFromCollectionsContainer(container: CollectionsContainer<List<String>,List<String>,List<String>,List<String>,List<String>>) = container.collectionsContainerIterator() .map(similarRestaurantsList){
            val (restaurantName, restaurantPriceTag, restaurantDiningCategory, restaurantDetailLink, restaurantThumbnail) = it
            RestaurantImpl(restaurantName = restaurantName, restaurantLink = restaurantDetailLink,
                restaurantDiningCategory = restaurantDiningCategory, restaurantPriceRating = restaurantPriceTag, restaurantThumbnailLink = restaurantThumbnail)
        }

    override suspend fun getAllRestaurantDetails(restaurantLink: String): RestaurantDetails {
        val scraperRequestInstance by lazy { ScraperRequest(urlToBeUsed = restaurantLink) }
       return scraperRequestInstance.response {
            htmlDocument {
                val restaurantsSlideShowPictureArray= generateRestaurantSlideShowPicturesFromDocElement(this)
                val basicAmenitiesArray =  generateRestaurantBasicAmenitiesFromDocElement(this)
               val restaurantDescription= generateRestaurantDescriptionFromDocElement(this)
               val restaurantMenuItems = generateRestaurantMenuItemsFromDocElement(this)
               val restaurantContactDetails= generateRestaurantContactDetailsFromDocElement(this)

               val restaurantOpeningHoursArray = generateRestaurantOpeningHoursFromDocElement(this)

                val similarRestaurantsList = generateListOfSimilarRestaurantsFromDocElement(this)
                return@htmlDocument RestaurantDetailsImpl(restaurantDescription = restaurantDescription, similarRestaurants =
                similarRestaurantsList, openingHours = restaurantOpeningHoursArray,
                restaurantContactList = restaurantContactDetails, restaurantMenuItems = restaurantMenuItems,
                restaurantAmenities = basicAmenitiesArray, restaurantSlideShowLinks = restaurantsSlideShowPictureArray)
            }
        }
    }

}