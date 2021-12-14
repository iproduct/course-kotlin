import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.10"
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("idea")
    kotlin("plugin.allopen") version "1.4.32"
    kotlin("kapt") version "1.4.32"
}

println("Kotlin version used is ${org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION}")
buildscript {
    val kotlinVersion = "1.6.10"
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-sam-with-receiver:$kotlinVersion")
    }
}
apply(plugin = "kotlin-sam-with-receiver")

group = "course.kotlin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation ("org.webjars:jquery:3.3.1-2")
    implementation ("org.webjars:bootstrap:4.3.1")
    implementation ("org.springframework.boot:spring-boot-starter-webflux")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("mysql:mysql-connector-java")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation ("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}
