import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
    id("com.diffplug.gradle.spotless") version("3.27.0")
    id("maven-publish")
    application
}

group = "org.zhi-dao.backend"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.zhi-dao.backend"
            artifactId = "Zhi-Dao-Backend"
            version = "1.0"

            from(components["java"])
        }
    }
}
dependencies {
    implementation("it.skrape:skrapeit:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.1.3")
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target(fileTree(rootDir) { include("**/*.kt") })

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
    encoding("UTF-8")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
