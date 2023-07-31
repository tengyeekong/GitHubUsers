object Testing {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9"
    const val jUnit = "junit:junit:4.13.1"
    const val mockk = "io.mockk:mockk:1.13.5"
}

object Dependencies {
    private const val kotlinVer = "1.8.20"

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVer"
    const val kotlinJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVer"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVer"
}

object Hilt {
    private const val hiltVer = "2.47"

    const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVer"
    const val hilt = "com.google.dagger:hilt-android:$hiltVer"
    const val compiler = "com.google.dagger:hilt-compiler:$hiltVer"
    const val compose = "androidx.hilt:hilt-navigation-compose:1.1.0-alpha01"
}

object Retrofit {
    private const val retrofitVer = "2.9.0"

    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVer"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVer"
    const val okHttp3Logging = "com.squareup.okhttp3:logging-interceptor:4.9.1"
}

object Moshi {
    private const val moshiVer = "1.11.0"

    const val kotlin = "com.squareup.moshi:moshi-kotlin:$moshiVer"
    const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVer"
}

object Coroutines {
    private const val coroutineVer = "1.5.2"

    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVer"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVer"
}

object Android {
    private const val lifecycleVer = "2.4.0"
    private const val navigationVer = "2.6.0"
    private const val roomVer = "2.5.2"

    const val gradle = "com.android.tools.build:gradle:8.0.2"
    const val materialDesign = "com.google.android.material:material:1.4.0"
    const val coreKtx = "androidx.core:core-ktx:1.7.0"
    const val paging = "androidx.paging:paging-runtime:3.1.0-beta01"
    const val room = "androidx.room:room-ktx:$roomVer"
    const val roomCompiler = "androidx.room:room-compiler:$roomVer"
    const val roomPaging = "androidx.room:room-paging:$roomVer"
}

object Compose {
    const val composeCompilerVer = "1.4.5"
    private const val composeVer = "1.5.0-alpha02"

    const val ui = "androidx.compose.ui:ui:$composeVer"
    const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVer"
    const val foundation = "androidx.compose.foundation:foundation:$composeVer"
    const val material = "androidx.compose.material:material:$composeVer"
    const val materialIcons = "androidx.compose.material:material-icons-extended:$composeVer"
    const val runtime = "androidx.compose.runtime:runtime:$composeVer"
    const val tooling = "androidx.compose.ui:ui-tooling:$composeVer"

    const val coil = "io.coil-kt:coil-compose:2.4.0"
    const val shimmer = "com.valentinilk.shimmer:compose-shimmer:1.0.5"

    const val paging = "androidx.paging:paging-compose:3.2.0"
    const val activity = "androidx.activity:activity-compose:1.7.0"
    const val navigation = "androidx.navigation:navigation-compose:2.7.0-rc01"
}
