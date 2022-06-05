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
package models

import constants.cityViewLink
import constants.dateNightLink
import constants.greatDrinksLink
import constants.instagramWorthyLink
import constants.outdoorSeatingLink
import constants.petFriendlyLink
import constants.vegansAndVegetariansLinks
import constants.wineSelectionLink

enum class RestaurantCollections(
  val restaurantCollectionName: String,
  val isCollectionPaginated: Boolean,
  val restaurantCollectionLink: String
) {

  OutdoorSeating("Outdoor Seating", true, outdoorSeatingLink),
  DateNight("Date Nights", false, dateNightLink),
  GramWorthy("Instagram Worthy", false, instagramWorthyLink),
  NairobiView("Views of the City", false, cityViewLink),
  Vegans("Vegetarians and Vegans", true, vegansAndVegetariansLinks),
  WineLovers("Wine Lovers", true, wineSelectionLink),
  Cocktails("Cocktails", true, greatDrinksLink),
  PetFriendly("Pet Friendly", false, petFriendlyLink)
}
