
import ninja.bryansills.findVersionNumber
import ninja.bryansills.findVersionString
import ninja.bryansills.javaVersion
import ninja.bryansills.libs

plugins {
    id("ninja-bryansills-spotless")
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = libs.findVersionNumber("compile-sdk")
    defaultConfig {
        minSdk = libs.findVersionNumber("min-sdk")
        targetSdk = libs.findVersionNumber("target-sdk")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    add("implementation", libs.findLibrary("core-ktx").get())

    add("implementation", libs.findLibrary("coroutines").get())
    add("implementation", libs.findLibrary("coroutines-android").get())

    val composeBom = libs.findLibrary("androidx-compose-bom").get()
    add("implementation", platform(composeBom))
    add("androidTestImplementation", platform(composeBom))

    add("implementation", libs.findLibrary("androidx-compose-material3").get())
    add("implementation", libs.findLibrary("androidx-compose-preview").get())
    add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())

    add("implementation", libs.findLibrary("hilt-android").get())
    add("ksp", libs.findLibrary("hilt-compiler").get())

    add("testImplementation", libs.findLibrary("junit").get())
    add("androidTestImplementation", libs.findLibrary("androidx-test-junit").get())
    add("androidTestImplementation", libs.findLibrary("androidx-test-runner").get())
}
