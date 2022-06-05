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
package models

enum class AllCuisines(override val cuisineName: String, override val cuisineLink: String) : Cuisines {
  SteakCuisine("Steak", "https://eatout.co.ke/cuisine/steak-house/?city=nairobi"),
  IndianCuisine("Indian", "https://eatout.co.ke/cuisine/indian/?city=nairobi"),
  EthiopianCuisine("Ethiopian", "https://eatout.co.ke/cuisine/ethiopian/?city=nairobi"),
  BarbequeCuisine("Barbeque", "https://eatout.co.ke/cuisine/barbeque/?city=nairobi"),
  AfricanCuisine("African", "https://eatout.co.ke/cuisine/african/?city=nairobi"),
  LebaneseCuisine("Lebanese", "https://eatout.co.ke/cuisine/lebanese/?city=nairobi"),
  EritreanCuisine("Eritrean", "https://eatout.co.ke/cuisine/eritrean/?city=nairobi"),
  FrenchCuisine("French", "https://eatout.co.ke/cuisine/french/?city=nairobi"),
  SpanishCuisine("Spanish", "https://eatout.co.ke/cuisine/spanish/?city=nairobi"),
  MexicanCuisine("Mexican", "https://eatout.co.ke/cuisine/mexican/?city=nairobi"),
  MediterraneanCuisine("Mediterranean", "https://eatout.co.ke/cuisine/mediterranean/?city=nairobi"),
  ItalianCuisine("Italian", "https://eatout.co.ke/cuisine/italian/?city=nairobi"),
  VegetarianCuisine("Vegetarian", "https://eatout.co.ke/cuisine/vegetarian/?city=nairobi"),
  CaribbeanCuisine("Caribbean", "https://eatout.co.ke/cuisine/caribbean/?city=nairobi"),
  SeaFoodCuisine("Sea Food", "https://eatout.co.ke/cuisine/seafood/?city=nairobi"),
  PeruvianCuisine("Peruvian", "https://eatout.co.ke/cuisine/peruvian/?city=nairobi"),
  LatinCuisine("Latin", "https://eatout.co.ke/cuisine/latin/?city=nairobi"),
  PanAsianCuisine("Pan Asian", "https://eatout.co.ke/cuisine/pan-asian/?city=nairobi"),
  JapaneseCuisine("Japanese", "https://eatout.co.ke/cuisine/japanese/?city=nairobi"),
  BurgerCuisine("Burger", "https://eatout.co.ke/cuisine/burger/?city=nairobi");
  companion object {
    fun listOfAllCuisines() = AllCuisines.values()
  }
}
