plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

group = "com.github.sean-nam-dev"
version = "1.0.0"

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
            groupId = "com.github.sean-nam-dev"
            artifactId = "kvov"
            version = "1.0.0"
        }
    }
    repositories {
        mavenLocal()
    }
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
}
