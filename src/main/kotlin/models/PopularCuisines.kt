package models

data class PopularCuisines(
    override val cuisineName: String,
    val cuisineThumbnailLink: String,
    override val cuisineLink: String
):Cuisines
