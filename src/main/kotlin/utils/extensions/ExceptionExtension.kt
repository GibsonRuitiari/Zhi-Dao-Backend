package utils

import java.net.ConnectException
import java.net.UnknownHostException



const val ANSI_RED = "\u001B[31m"
const val ANSI_RESET ="\u001B[0m"
fun Exception.parseExceptionToReadableFormat():String = when(this){
        is UnknownHostException, is ConnectException->"Scraping failed due to client's internet connection"
        else->{
            val firstElement = stackTrace.firstOrNull()
            firstElement?.let {
                println("${ANSI_RED}An exception in ${it.fileName} occurred at $firstElement ${System.lineSeparator()}and it was caused by $this $ANSI_RESET")
            }
            "An unknown error occurred. Please try again later."
        }
}






