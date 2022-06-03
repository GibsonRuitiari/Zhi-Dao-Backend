package utils

import constants.baseUrl
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Request
import it.skrape.fetcher.Scraper
import it.skrape.fetcher.skrape

object ScraperRequest {
     operator fun invoke(urlToBeUsed:String= baseUrl):Scraper<Request> = skrape(HttpFetcher){ request { url= urlToBeUsed} }
}
