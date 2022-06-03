package models.scrapingResult

sealed class ScraperResult<T>(val data:T?=null, val errorMessage:String?=null)
class ScrapingSuccess<T>(data:T):ScraperResult<T>(data,null)
class ScrapingError<T>(errorMessage:String?):ScraperResult<T>(null,errorMessage)


