
import ninja.bryansills.findVersionNumber
import ninja.bryansills.findVersionString
import ninja.bryansills.javaVersion
import ninja.bryansills.libs

plugins {
    id("ninja-bryansills-spotless")
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
}

android {
    compileSdk = libs.findVersionNumber("compile-sdk")
    defaultConfig {
        minSdk = libs.findVersionNumber("min-sdk")
        targetSdk = libs.findVersionNumber("target-sdk")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = libs.javaVersion
        targetCompatibility = libs.javaVersion
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = libs.javaVersion.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.findVersionString("compose-compiler")
    }
}

dependencies {
    add("coreLibraryDesugaring", libs.findLibrary("android-desugarJdkLibs").get())

    val composeBom = libs.findLibrary("androidx-compose-bom").get()
    add("implementation", platform(composeBom))
    add("androidTestImplementation", platform(composeBom))

    add("implementation", libs.findLibrary("androidx-compose-material3").get())
    add("implementation", libs.findLibrary("androidx-compose-preview").get())
    add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())

//    implementation(libs.findLibrary("androidx-compose-activity"))
}