package models

interface RestaurantDetails {
    val restaurantDescription:String
    val similarRestaurants:List<Restaurant>
    val openingHours:List<String> // 12:00-22:00 and so on
    val restaurantContactList:List<RestaurantContacts> // collection of restaurant name,email, google pin and mobile phone number
    val restaurantMenuItems:List<RestaurantMenuItem> // an array of menu items from wine, side, main dish etc
    val restaurantAmenities:List<RestaurantAmenities> // an array of amenities present/available at the restaurant
    val restaurantSlideShowLinks:List<String> // an array of pictures that show how the restaurant looks like

}