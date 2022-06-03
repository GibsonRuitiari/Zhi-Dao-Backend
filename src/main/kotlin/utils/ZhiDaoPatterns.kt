package utils

object ZhiDaoPatterns {
    val diningWordRegexPattern = "\\w+ Dining".toRegex()
    val priceRatingRegexPattern = "[\$]{1,}".toRegex()
    val restaurantNameRegexPattern = "(?!.*Dining) .*[^\$]{1,}".toRegex()
}