package utils.extensions

import java.net.UnknownHostException


const val ANSI_RED = "\u001B[31m"
const val ANSI_RESET ="\u001B[0m"
fun Exception.parseExceptionToReadableFormat():String = when(this){
        is UnknownHostException->"Scraping failed due to client's internet connection. The cause of exception was $this"
        else->{
            val firstElement = stackTrace.firstOrNull()
            firstElement?.let {
                val errorMessage ="${ANSI_RED}An exception in ${it.fileName} occurred at $firstElement ${System.lineSeparator()}and it was caused by $this $ANSI_RESET"
                println(errorMessage)
            }
            "An unknown error occurred. Please try again later."
        }
}






