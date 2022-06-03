package models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RestaurantImpl(
   @SerialName("title") override val restaurantName: String,
   @SerialName("image_url") override val restaurantThumbnailLink: String,
   @SerialName("url") override val restaurantLink: String,
   override val restaurantDiningCategory: String?=null,
   @SerialName("price") override val restaurantPriceRating: String,
):Restaurant