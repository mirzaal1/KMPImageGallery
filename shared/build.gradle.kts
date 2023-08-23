plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    alias(libs.plugins.detekt)
    alias(libs.plugins.serialization)
}


kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)

                //sharedVm
                api(libs.kmmViewmodelCore)

                //di
                api(libs.koinCore)

                //network
                implementation(libs.ktorClientCore)
                implementation(libs.ktorClientJson)
                implementation(libs.ktorClientLogging)
                implementation(libs.ktorClientContentNegotiation)
                implementation(libs.ktorSerializationKotlinxJson)
                implementation(libs.kotlinxSerializationCore)

                //imageloading
                implementation(libs.imageLoader)

                // KMP Dialogs
                implementation(libs.composeMaterialDialogsCore)

                //coroutines
                implementation(libs.kotlinxCoroutinesCore)

                // local
                implementation(libs.multiplatformSettings)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.koin)
                implementation(libs.ktorClient)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktorClientIos)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonMain)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.mirzaal1.kmpimagegallery"
    compileSdk = 33

    sourceSets["main"].manifest.srcFile("src/Main/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/Main/res")

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }

}

detekt {
    source.from(files(rootProject.rootDir))
    parallel = true
    autoCorrect = true
    buildUponDefaultConfig = true
}
