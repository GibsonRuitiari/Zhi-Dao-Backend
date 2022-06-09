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
package utils.extensions

import java.net.UnknownHostException

const val ANSI_RED = "\u001B[31m"
const val ANSI_RESET = "\u001B[0m"
internal fun Exception.parseExceptionToReadableFormat(): String = when (this) {
  is UnknownHostException -> "Scraping failed due to client's internet connection. The cause of exception was $this"
  else -> {
    val firstElement = stackTrace.firstOrNull()
    firstElement?.let {
      val errorMessage = "${ANSI_RED}An exception in ${it.fileName} occurred at $firstElement ${System.lineSeparator()}and it was caused by $this $ANSI_RESET"
      println(errorMessage)
    }
    "An unknown error occurred. Please try again later."
  }
}
