import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.pluginSerialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.googleServices)
//    alias(libs.plugins.firebaseCrashlytics)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.guilherme")
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.driver.android)
            implementation("androidx.core:core-splashscreen:1.0.1")

            //Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)
        }

        commonMain.dependencies {

            //Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
//            implementation(libs.koin.ktor)

            //Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.auth)

            //Coroutines
            implementation(libs.kotlinx.coroutines.core)

            //Serialization
            implementation(libs.serialization.json)

            //Material icons extended
            implementation(libs.material.icons.extended)

            //ViewModel
            implementation(libs.androidx.lifecycle.viewmodel)

            //SqlDelight
            implementation(libs.sqldelight.coroutine.extensions)

        }
    }
}

android {
    namespace = "com.guilherme.wheretowatch.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
