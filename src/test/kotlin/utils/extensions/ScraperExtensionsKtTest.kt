package utils.extensions

import com.google.common.truth.Truth.assertThat
import constants.baseUrl
import constants.featuredRestaurantsSectionCssSelector
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Request
import it.skrape.fetcher.Scraper
import it.skrape.fetcher.skrape
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.net.UnknownHostException

internal class ScraperExtensionsKtTest {
    private var scraperRequestInstance:Scraper<Request> ?=null
    private var urlToBeUsed = baseUrl
    @BeforeAll
    fun setUp(){
        scraperRequestInstance = skrape(HttpFetcher){
            request {
                url= urlToBeUsed
            }
        }
    }

    @AfterAll
    fun tearDown(){
        scraperRequestInstance = null
    }
    @Test
   suspend fun scrapeGeneralRestaurantsDetails() {
        kotlin.runCatching {
            scraperRequestInstance!!.scrapeGeneralRestaurantsDetails(featuredRestaurantsSectionCssSelector)
        }.onFailure {
            if (it is UnknownHostException){
                assertThat(it.message).contains("No such host is known (eatout.co.ke)")
            }
        }.onSuccess {
            assertThat(it).isNotNull()
            val (firstParam,_,_,_,_) = it
            assertThat(firstParam).containsNoDuplicates()
        }
    }


}