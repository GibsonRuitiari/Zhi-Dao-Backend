import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
    application
}

group = "org.zhi-dao.backend"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("it.skrape:skrapeit:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
   implementation("it.skrape:skrapeit-browser-fetcher:1.1.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation ("org.jsoup:jsoup:1.15.1")
    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.1.3")
    implementation(kotlin("reflect"))
}



tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}