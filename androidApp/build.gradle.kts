plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}


kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                api(libs.androidxActivityCompose)
                api(libs.androidxAppcompat)
                api(libs.androidxCoreKtx)
            }
        }
    }
}


android {
    namespace = "com.mirzaal1.kmpimagegallery.android"
    compileSdk = 33

    sourceSets["main"].manifest.srcFile("src/Main/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.mirzaal1.kmpimagegallery.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
/*
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }*/
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
