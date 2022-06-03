package models

import constants.*

enum class RestaurantCollections(   val restaurantCollectionName:String,
                                    val isCollectionPaginated:Boolean,
                                    val restaurantCollectionLink:String) {

    OutdoorSeating("Outdoor Seating",true, outdoorSeatingLink),
    DateNight("Date Nights",false, dateNightLink),
    GramWorthy("Instagram Worthy",false, instagramWorthyLink),
    NairobiView("Views of the City",false, cityViewLink),
    Vegans("Vegetarians and Vegans",true, vegansAndVegetariansLinks),
    WineLovers("Wine Lovers",true, wineSelectionLink),
    Cocktails("Cocktails",true, greatDrinksLink),
    PetFriendly("Pet Friendly",false, petFriendlyLink)
}