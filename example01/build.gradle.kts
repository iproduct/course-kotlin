val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.30"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.5.30"
    idea
}

idea {
    module {
        setDownloadJavadoc(true)
        setDownloadSources(true)
    }
}

group = "org.iproduct.ktor"
version = "0.0.1"
application {
    mainClass.set("org.iproduct.ktor.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

    implementation ("io.ktor:ktor-html-builder:$ktor_version")
    implementation ("io.ktor:ktor-gson:$ktor_version")
    implementation ("io.ktor:ktor-client-core:$ktor_version")
    implementation ("io.ktor:ktor-client-core-jvm:$ktor_version")
    implementation ("io.ktor:ktor-metrics:$ktor_version")
    implementation ("io.dropwizard.metrics:metrics-core:3.1.2")
    implementation ("com.codahale.metrics:metrics-core:3.0.2")
    implementation ("io.ktor:ktor-metrics:$ktor_version")
    implementation ("io.dropwizard.metrics:metrics-core:3.1.2")
    implementation ("com.codahale.metrics:metrics-core:3.0.2")


}
