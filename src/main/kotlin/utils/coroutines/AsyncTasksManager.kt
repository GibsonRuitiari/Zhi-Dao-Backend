package utils.coroutines

import kotlinx.coroutines.Deferred
import models.scrapingResult.ScraperResult

interface AsyncTasksManager {
    suspend fun <T> performTaskAsync(codeToBeExecuted:suspend ()->T):Deferred<T>
    suspend fun <T> performTaskAsynchronouslyAndAwaitForResult(codeToBeExecuted: suspend ()->T):ScraperResult<T>
    fun cancelAllAsynchronousOperations()
    fun cleanUpResources()
}