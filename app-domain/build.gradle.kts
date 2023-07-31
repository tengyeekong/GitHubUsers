plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.tengyeekong.githubusers.domain"
    compileSdk = Versions.TARGET_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(project(":common"))

    // Coroutines
    implementation(Coroutines.core)

    // Moshi
    implementation(Moshi.kotlin)
    kapt(Moshi.codeGen)

    // Hilt
    implementation(Hilt.hilt)

    // Architecture Components
    implementation(Android.paging)
}