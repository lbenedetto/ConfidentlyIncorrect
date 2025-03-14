
plugins {
    alias(libs.plugins.boot)
    alias(libs.plugins.depman)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

group = "com.larsbenedetto"
version = "1.1"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.logging)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.thymeleaf)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.webmvc)
    implementation(libs.thymeleaf.layout.dialect)

    runtimeOnly(libs.mysql.connector.java)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.devtools)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmToolchain(21)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
