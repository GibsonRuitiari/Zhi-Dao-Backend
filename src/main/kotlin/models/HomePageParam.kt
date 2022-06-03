package models

data class HomePageParam(val headerTitleAndSubtitle:HomePageHeaders, val sectionCssSelector: String,
                         val collections: RestaurantCollections?,
                         val destinationCollection:MutableList<HomePageRestaurants>)
