import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
    idea
}

group = "course.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    runtimeOnly ("org.jetbrains.kotlinx:kotlinx-coroutines-jdk9:1.8.0")
    runtimeOnly ("ch.qos.logback:logback-classic:1.2.11")
    implementation ("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.8.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation ("io.reactivex.rxjava3:rxkotlin:3.0.1")
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

idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
}
