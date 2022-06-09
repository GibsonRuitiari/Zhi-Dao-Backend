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
package utils.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import models.scrapingResult.ScraperResult
import models.scrapingResult.ScrapingError
import models.scrapingResult.ScrapingSuccess
import utils.extensions.parseExceptionToReadableFormat

internal class DefaultAsyncTasksHandler : AsyncTasksManager {
  private val currentDeferredObjects: MutableList<Deferred<*>> = mutableListOf()

  override suspend fun <T> performTaskAsync(codeToBeExecuted: suspend () -> T): Deferred<T> = coroutineScope {
    val deferredJob: Deferred<T> = async(Dispatchers.IO) { codeToBeExecuted() }
    currentDeferredObjects.add(deferredJob)
    deferredJob.invokeOnCompletion { currentDeferredObjects.remove(deferredJob) }
    return@coroutineScope deferredJob
  }

  override suspend fun <T> performTaskAsynchronouslyAndAwaitForResult(codeToBeExecuted: suspend () -> T): ScraperResult<T> {
    return try {
      val scrapedData = performTaskAsync(codeToBeExecuted).await()
      println(currentDeferredObjects.size)
      ScrapingSuccess(scrapedData)
    } catch (ex: Exception) {
      println(currentDeferredObjects.size)
      if (ex !is CancellationException) ScrapingError(ex.parseExceptionToReadableFormat(), ex)
      else throw ex
    }
  }
  override fun cancelAllAsynchronousOperations() {
    val deferredObjectsSize = currentDeferredObjects.size
    if (deferredObjectsSize > 0) {
      for (i in deferredObjectsSize - 1 downTo 0) {
        currentDeferredObjects[i].cancel()
      }
    }
  }
  override fun cleanUpResources() {
    cancelAllAsynchronousOperations()
  }
}
