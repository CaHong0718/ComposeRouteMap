import java.util.Properties

//local properties load
val props = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version libs.versions.kotlin.get()
    id("com.google.dagger.hilt.android") version "2.50"
    id("kotlin-kapt")
}

android {
    namespace = "com.example.composeroutemap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.composeroutemap"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //네이버 지도
        manifestPlaceholders["NAVER_CLIENT_ID"] = props.getProperty("naver_api_key")

        buildConfigField("String", "NAVER_OPENAPI_CLIENT_ID", "\"${props.getProperty("NAVER_OPENAPI_CLIENT_ID")}\"")
        buildConfigField("String", "NAVER_OPENAPI_CLIENT_SECRET", "\"${props.getProperty("NAVER_OPENAPI_CLIENT_SECRET")}\"")
        buildConfigField("String", "NAVER_MAP_API_CLIENT_ID", "\"${props.getProperty("naver_api_key")}\"")
        buildConfigField("String", "NAVER_MAP_API_CLIENT_SECRET", "\"${props.getProperty("NAVER_MAP_API_CLIENT_SECRET")}\"")

        resValue(
            "string",
            "naver_openapi_client_id",
            props["NAVER_OPENAPI_CLIENT_ID"] as String
        )
        resValue(
            "string",
            "naver_openapi_client_secret",
            props["NAVER_OPENAPI_CLIENT_SECRET"] as String
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //네이버 지도/////////
    implementation ("io.github.fornewid:naver-map-compose:1.8.2")
    implementation ("io.github.fornewid:naver-map-location:16.0.0")
    ////////////////////
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    kapt ("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
}