package utils.coroutines

import kotlinx.coroutines.*
import models.scrapingResult.ScraperResult
import models.scrapingResult.ScrapingError
import models.scrapingResult.ScrapingSuccess
import utils.parseExceptionToReadableFormat

open class DefaultAsyncTasksHandler:AsyncTasksManager {
    private val currentDeferredObjects:MutableList<Deferred<*>> = mutableListOf()

    override suspend fun <T> performTaskAsync(codeToBeExecuted: suspend () -> T): Deferred<T> = coroutineScope{
         val deferredJob:Deferred<T> = async(Dispatchers.IO) {codeToBeExecuted()}
         currentDeferredObjects.add(deferredJob)
         deferredJob.invokeOnCompletion {currentDeferredObjects.remove(deferredJob)}
         return@coroutineScope deferredJob
    }

    override suspend fun <T> performTaskAsynchronouslyAndAwaitForResult(codeToBeExecuted: suspend () -> T): ScraperResult<T> {
        return try {
           val scrapedData= performTaskAsync(codeToBeExecuted).await()
            ScrapingSuccess(scrapedData)
        }catch (ex:Exception){
            if (ex !is CancellationException) ScrapingError(ex.parseExceptionToReadableFormat())
            else throw ex
        }
    }
    override fun cancelAllAsynchronousOperations() {
        val deferredObjectsSize = currentDeferredObjects.size
        if (deferredObjectsSize >0){
            for (i in deferredObjectsSize-1 downTo 0){
                currentDeferredObjects[i].cancel()
            }
        }
    }
    override fun cleanUpResources() {
       cancelAllAsynchronousOperations()

    }


}