plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tengyeekong.githubusers.ui"
    compileSdk = Versions.TARGET_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVer
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
    implementation(project(":app-domain"))

    // Kotlin
    implementation(Dependencies.kotlin)
    implementation(Dependencies.kotlinJdk)

    // Coroutines
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    // Architecture Components
    implementation(Android.paging)

    // Compose
    implementation(Compose.ui)
    implementation(Compose.toolingPreview)
    implementation(Compose.foundation)
    implementation(Compose.material)
    implementation(Compose.materialIcons)
    implementation(Compose.runtime)
    debugImplementation(Compose.tooling)
    implementation(Compose.coil)
    implementation(Compose.shimmer)
    implementation(Compose.paging)
    implementation(Compose.activity)
    implementation(Compose.navigation)

    // Material Design
    implementation(Android.materialDesign)

    // Moshi
    implementation(Moshi.kotlin)

    // Hilt
    implementation(Hilt.hilt)
    implementation(Hilt.compose)
    kapt(Hilt.compiler)

    // Testing
    testImplementation(Testing.jUnit)
    testImplementation(Testing.mockk)
}