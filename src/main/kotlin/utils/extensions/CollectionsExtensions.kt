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

// can hold any type of iterables --> lists/sets
data class CollectionsContainer<out A, out B, out C, out D, out E>(
  val firstCollection: A,
  val secondCollection: B,
  val thirdCollection: C,
  val fourthCollection: D,
  val fifthCollection: E
)

inline fun <T> Iterator<T>.forEach(performActionOnObject: (T) -> Unit) {
  while (hasNext()) {
    val obj = next()
    performActionOnObject(obj)
  }
}

inline fun <T, R, C : MutableCollection<in R>> Iterator<T>.map(destination: C, performActionOnObjectAndReturnAnother: (T) -> R): C {
  while (hasNext()) {
    val nextObj = next()
    destination.add(performActionOnObjectAndReturnAnother(nextObj))
  }
  return destination
}

fun <A> Triple<Iterable<A>, Iterable<A>, Iterable<A>>.collectionsContainerIterator(): Iterator<Triple<A, A, A>> {
  val param0 = first.iterator()
  val param1 = second.iterator()
  val param2 = third.iterator()
  return object : Iterator<Triple<A, A, A>> {
    override fun hasNext(): Boolean {
      return param0.hasNext() && param1.hasNext() && param2.hasNext()
    }

    override fun next(): Triple<A, A, A> {
      return Triple(param0.next(), param1.next(), param2.next())
    }
  }
}

fun <A> CollectionsContainer<Iterable<A>, Iterable<A>, Iterable<A>, Iterable<A>, Iterable<A>>.collectionsContainerIterator(): Iterator<CollectionsContainer<A, A, A, A, A>> {
  val param0 = firstCollection.iterator()
  val param1 = secondCollection.iterator()
  val param2 = thirdCollection.iterator()
  val param3 = fourthCollection.iterator()
  val param4 = fifthCollection.iterator()
  return object : Iterator<CollectionsContainer<A, A, A, A, A>> {
    override fun hasNext(): Boolean {
      return param0.hasNext() && param1.hasNext() && param2.hasNext() && param3.hasNext() && param4.hasNext()
    }
    override fun next(): CollectionsContainer<A, A, A, A, A> {
      return CollectionsContainer(
        param0.next(), param1.next(), param2.next(), param3.next(),
        param4.next()
      )
    }
  }
}
