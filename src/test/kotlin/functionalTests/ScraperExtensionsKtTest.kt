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
package functionalTests

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
import utils.extensions.scrapeGeneralRestaurantsDetails
import java.net.UnknownHostException

class ScraperExtensionsKtTest {
  private var scraperRequestInstance: Scraper<Request> ? = null
  private var urlToBeUsed = baseUrl
  @BeforeAll
  fun setUp() {
    scraperRequestInstance = skrape(HttpFetcher) {
      request {
        url = urlToBeUsed
      }
    }
  }

  @AfterAll
  fun tearDown() {
    scraperRequestInstance = null
  }
  @Test
  suspend fun testTheScrapeGeneralRestaurantsDetailsMethodAndEnsureItReturnsExpectedValueDuringExceptionAndWithoutException() {
    kotlin.runCatching {
      scraperRequestInstance!!.scrapeGeneralRestaurantsDetails(featuredRestaurantsSectionCssSelector)
    }.onFailure {
      if (it is UnknownHostException) {
        assertThat(it.message).contains("No such host is known (eatout.co.ke)")
      }
    }.onSuccess {
      assertThat(it).isNotNull()
      val (firstParam, _, _, _, _) = it
      assertThat(firstParam).containsNoDuplicates()
    }
  }
}
