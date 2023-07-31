plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tengyeekong.githubusers"
    compileSdk = Versions.TARGET_SDK
    buildToolsVersion = Versions.BUILD_TOOLS

    defaultConfig {
        applicationId = "com.tengyeekong.githubusers"
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = Versions.VERSION_CODE
        versionName = Versions.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
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
    implementation(project(":app-data"))
    implementation(project(":app-domain"))
    implementation(project(":app-ui"))

    // Kotlin
    implementation(Dependencies.kotlin)
    implementation(Dependencies.kotlinJdk)

    // Material Design
    implementation(Android.materialDesign)

    // Moshi
    implementation(Moshi.kotlin)

    // Hilt
    implementation(Hilt.hilt)
    kapt(Hilt.compiler)

    // Room
    implementation(Android.room)
}
