import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
    id("com.diffplug.gradle.spotless") version("3.23.0")
    application
}

group = "org.zhi-dao.backend"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("it.skrape:skrapeit:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.1.3")

}


configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        // by default the target is every '.kt' and '.kts` file in the java sourcesets
        ktlint("0.43.2").userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
        licenseHeaderFile(file("$rootDir/src/spotless/spotless.kotlin.license"))
    }
    kotlinGradle {
        target("*.gradle.kts") // default target for kotlinGradle
        ktlint("0.43.2")
        endWithNewline()
        trimTrailingWhitespace()
    }
    format("misc"){
        target(fileTree(rootDir){ include("*.gradle.kts","**/*.gitignore","README.md")})
        trimTrailingWhitespace()
        endWithNewline()
    }
    encoding("UTF-8")
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