package models

data class RestaurantDetailsImpl(
    override val restaurantDescription: String,
    override val similarRestaurants: List<Restaurant>,
    override val openingHours: List<String>,
    override val restaurantContactList: List<RestaurantContacts>,
    override val restaurantMenuItems: List<RestaurantMenuItem>,
    override val restaurantAmenities: List<RestaurantAmenities>,
    override val restaurantSlideShowLinks: List<String>
):RestaurantDetails