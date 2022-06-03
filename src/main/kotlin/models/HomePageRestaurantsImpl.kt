package models

data class HomePageRestaurantsImpl(
    override val titleAndSubtitle: HomePageHeaders,
    override val restaurantName: String,
    override val restaurantThumbnailLink: String,
    override val restaurantLink: String,
    override val restaurantDiningCategory: String,
    override val restaurantPriceRating: String,
    val collection:RestaurantCollections?
):HomePageRestaurants