plugins {
    kotlin("jvm") version "2.0.20"
}

group = "me.study"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}