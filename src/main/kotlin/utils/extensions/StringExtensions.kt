package utils.extensions

import utils.ZhiDaoPatterns

fun findMatchingResultsFromRegularExpression(inputToBeMatched:String):Triple<String?,String?,String?>{
    val nameResult= ZhiDaoPatterns.restaurantNameRegexPattern.find(inputToBeMatched)?.value?.trim()
    val priceRatingResult = ZhiDaoPatterns.priceRatingRegexPattern.find(inputToBeMatched)?.value?.trim()
    val diningCategoryResult = ZhiDaoPatterns.diningWordRegexPattern.find(inputToBeMatched)?.value
    return Triple(nameResult,priceRatingResult,diningCategoryResult)
}