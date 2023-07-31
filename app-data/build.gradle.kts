plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.tengyeekong.githubusers.data"
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

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":app-domain"))

    implementation(Android.coreKtx)

    // Coroutines
    implementation(Coroutines.core)

    // Hilt
    implementation(Hilt.hilt)

    // Room
    implementation(Android.room)
    annotationProcessor(Android.roomCompiler)
    kapt(Android.roomCompiler)
    implementation(Android.roomPaging)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitMoshiConverter)
    implementation(Retrofit.okHttp3Logging)
}