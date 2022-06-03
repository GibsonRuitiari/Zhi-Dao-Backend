package models

sealed class HomePageHeaders

data class IncredibleCityViewsHeader(val title:String,val subtitle: String):HomePageHeaders()
data class CosyCafesToWorkFromHeader(val title: String,val subtitle: String):HomePageHeaders()
data class PetFriendlyHeader(val title: String,val subtitle: String):HomePageHeaders()
data class AfterWorkDrinksHeader(val title: String,val subtitle: String):HomePageHeaders()
data class BestForVegansHeader(val title: String,val subtitle: String):HomePageHeaders()
data class BestForWineLoversHeader(val title: String,val subtitle: String):HomePageHeaders()
data class ForTheGramHeaderHeader(val title: String,val subtitle: String):HomePageHeaders()
data class GreatOutdoorsHeader(val title: String,val subtitle: String):HomePageHeaders()




