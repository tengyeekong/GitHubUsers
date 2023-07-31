plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.tengyeekong.githubusers.common"
    compileSdk = Versions.TARGET_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK

        buildConfigField("String", "API_KEY", "\"328c283cd27bd1877d9080ccb1604c91\"")
        buildConfigField("String", "IMG_URL_PREFIX", "\"https://image.tmdb.org/t/p/w500\"")
    }

    buildFeatures {
        buildConfig = true
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
    implementation(Dependencies.kotlinJdk)

    // Hilt
    implementation(Hilt.hilt)

    // Moshi
    implementation(Moshi.kotlin)
}
