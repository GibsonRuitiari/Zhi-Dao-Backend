package models

interface Restaurant {
    val restaurantName:String
    val restaurantThumbnailLink:String
    val restaurantLink:String
    val restaurantDiningCategory:String?
    val restaurantPriceRating:String
}