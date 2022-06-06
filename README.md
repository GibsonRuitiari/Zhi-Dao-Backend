**Zhi Dao (to-know)**

This project acts as backend to the Zhi-Dao android app (currently in progress) which is a restaurants guide app.

The main purpose of this project is to Nairobi restaurants' data from a website and present the data in a consumable form.

**Tech Stack**

This project utilizes several tech stack for example:

* [Skrapeit](https://github.com/skrapeit/skrape.it) A Kotlin-based testing/scraping/parsing library providing the ability to analyze and extract data from HTML (server & client-side rendered).
* [Coroutines ](https://github.com/Kotlin/kotlinx.coroutines) Kotlin library for asynchronous programming
* [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serialization-guide.md#:~:text=Kotlin%20Serialization%20is%20a%20cross-platform%20and%20multi-format%20framework,making%20sure%20only%20valid%20objects%20can%20be%20deserialized.).Kotlin Serialization is a cross-platform and multi-format framework for data serialization—converting trees of objects to strings, byte arrays, or other serial representations and back. Kotlin Serialization fully supports and enforces the Kotlin type system, making sure only valid objects can be deserialized.
* [Google Truth](https://github.com/google/truth) Fluent assertions for Java and Android


**Requirements**

1. Jdk 8+
2. Kotlin version 1.6.20
3. Gradle


**Usage**

Currently, the project is intended to be integrated with the android-app rather than it being a standalone project.
Nonetheless, the project is fit to be used standalone.
All you have to do is load the library in your project, and you are good to go.
For example, in build.gradle.kts

```
repositories {
maven{ url = uri("https://jitpack.io")}

}

dependencies {
implementation("com.github.GibsonRuitiari:Zhi-Dao-Backend:1.0")

}
```
Example [Fetch all cuisines offered by Restaurants in Nairobi]

```
class CuisinesUseCase(val cuisinesRepository:CuisinesRepository){

fun getAllCuisines(cuisineLink:String){
cuisinesRepository.getRestaurantsServingAParticularCuisinesWhenGivenACuisineLink(cuisineLink).also{
result->
// do something useful with the result }
}
```



**License**

Copyright 2022 Gibson Ruitiari

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
