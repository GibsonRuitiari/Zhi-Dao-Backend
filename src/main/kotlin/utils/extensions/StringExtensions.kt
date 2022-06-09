/*
 * Copyright 2022 Gibson Ruitiari.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package utils.extensions

import utils.ZhiDaoPatterns

internal fun findMatchingResultsFromRegularExpression(inputToBeMatched: String): Triple<String?, String?, String?> {
  val nameResult = ZhiDaoPatterns.restaurantNameRegexPattern.find(inputToBeMatched)?.value?.trim()
  val priceRatingResult = ZhiDaoPatterns.priceRatingRegexPattern.find(inputToBeMatched)?.value?.trim()
  val diningCategoryResult = ZhiDaoPatterns.diningWordRegexPattern.find(inputToBeMatched)?.value
  return Triple(nameResult, priceRatingResult, diningCategoryResult)
}
