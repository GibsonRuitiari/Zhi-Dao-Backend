package models

data class FeaturedRestaurantsHomePage(
    override val restaurantName: String,
    override val restaurantThumbnailLink: String,
    override val restaurantLink: String,
    override val restaurantDiningCategory: String,
    override val restaurantPriceRating: String,
    override val isRestaurantFeatured: Boolean
):FeaturedRestaurants