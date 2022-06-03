package constants

const val baseUrl="https://eatout.co.ke/"

// css selectors
// home page restaurants css selectors
const val restaurantsDetailsCssSelectorDiv ="place-item"
const val restaurantsDetailsCssSelectorThumbnailLinks="entry-thumb"
const val cuisinesDetailsSectionCssSelector="elementor-element-1521c61"
const val cuisinesDivCssClassSelector= "swiper-wrapper"
const val cuisinesTitleCssSelector ="title"
const val cuisinesImgCssSelector = "slide-wrapper"
const val outdoorSectionCssSelector ="elementor-element-792909a"
const val vegansHomePageCssSelector ="elementor-element-9da732d"
const val wineLoversSectionCssSelector ="elementor-element-933d675"
const val featuredRestaurantsSectionCssSelector ="elementor-element-1e226dd"
const val greatViewCitySectionCssSelector  ="elementor-element-8b80d60"
const val greatForGramSectionCssSelector ="elementor-element-42e8064"
const val petFriendlySectionCssSelector  ="elementor-element-ca71f75"
const val cosyCafesSectionCssSelector ="elementor-element-7227130"
const val afterWorkSectionCssSelector ="elementor-element-eba6db8"

// restaurant details css selectors
const val restaurantsSlideShowPicturesCssSelector ="margin-0"
const val restaurantsBasicAmenitiesCssSelector="show-grid"
const val restaurantsDescriptionDivCssSelector ="entry-visibility"
const val restaurantMenuItemsDivCssSelector="modula-items"
// a<>
const val restaurantMenuItemsACssSelector="data-image-id"
const val restaurantMenuItemsImgCssSelector="pic"
const val restaurantContactDetailsDivCssSelector ="entry-detail"
// a<target=_blank>
const val restaurantContactDetailAKeyAttributeSelector = "target"
const val restaurantContactDetailAValueAttributeSelector ="_blank"

const val restaurantOpeningHoursDivCssSelector="entry-detail"
const val restaurantOpeningHoursSpanCssSelector="open-time"

const val similarRestaurantsDivCssSelector="golo-slick-carousel"
const val similarRestaurantsDivAttributeKey="data-slick"

const val similarRestaurantsACssSelector= "entry-thumb"

const val similarRestaurantsDiningCategoriesDiv ="place-term"
const val similarRestaurantsH3CssSelector="place-title"
const val similarRestaurantsPriceTagsDivCssSelector="place-price"



// headers constants
const val overallHeadersTitle = "Explore curated lists of top restaurants and cafes based on trends."
const val afterWorkTitle="After-work Drinks"
const val afterWorkSubtitle="Great spaces for drink after work"

const val petFriendlyTitle="\uD83D\uDC36 Woof!"
const val petFriendlySubtitle="Pet-Friendly restaurants in your neighborhood."

const val gramTitle="\uD83D\uDCF8 For the Gram"
const val gramSubtitle="Stunning interiors, perfect for sharing on Instagram!"

const val cosyCafeTitle="\uD83D\uDECB Cosy Cafes To Work From"
const val cosyCafeSubtitle ="Why work from home? Check out these cute cafes."

const val cityViewTitle="\uD83C\uDF07 Incredible City Views"
const val cityViewSubtitle= "The best restaurants to enjoy Nairobi's glittering skyline."

const val wineTitle="\uD83E\uDD42 For Wine Lovers"
const val wineSubtitle="Our picks for the best wine lists in Nairobi."

const val vegansTitle="\uD83E\uDD57 Best For Vegans"
const val vegansSubtitle ="Unleash your inner vegan at these amazing restaurants."

const val outdoorTitle ="The Great Outdoors"
const val outdoorSubtitle ="Make the most of Nairobi's incredible weather."

// recommendations

const val vegansAndVegetariansLinks="https://eatout.co.ke/best-for/suitable-for-vegans-vegetarians/"
const val dateNightLink="https://eatout.co.ke/best-for/date-night"
const val outdoorSeatingLink="https://eatout.co.ke/best-for/outdoor-seating"
const val greatDrinksLink="https://eatout.co.ke/best-for/cocktails?city=nairobi"
const val instagramWorthyLink="https://eatout.co.ke/best-for/gram-worthycitynairobi/"
const val petFriendlyLink="https://eatout.co.ke/best-for/dog-pet-friendly"
const val cityViewLink="https://eatout.co.ke/best-for/view?city=nairobi"
const val wineSelectionLink="https://eatout.co.ke/best-for/wine-selection/"

// given a current term return the pagination link
val paginationLink:(currentTerm:String)->String = {currentTerm->
  ///  "https://eatout.co.ke//wp-admin//admin-ajax.php//page//2//page//2//page//2//?action=golo_pagination_ajax&title&item_amount=16&current_term=outdoor-seating&type_term=place-amenities&city=nairobi&location&place_layout=layout-01%5C%5C%22\\%22"
    "https://eatout.co.ke//wp-admin//admin-ajax.php//page//2//page//2//page//2//?action=golo_pagination_ajax&title&item_amount=16&current_term=$currentTerm&type_term=place-amenities&city=nairobi&location&place_layout=layout-01%5C%5C%22\\%22" }
