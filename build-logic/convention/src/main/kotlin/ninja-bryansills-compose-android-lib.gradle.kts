
import ninja.bryansills.findVersionNumber
import ninja.bryansills.javaVersion
import ninja.bryansills.libs

plugins {
    id("ninja-bryansills-spotless")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.android.library")
}

android {
    compileSdk = libs.findVersionNumber("compile-sdk")
    defaultConfig {
        minSdk = libs.findVersionNumber("min-sdk")

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
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    add("coreLibraryDesugaring", libs.findLibrary("android-desugarJdkLibs").get())
    add("implementation", libs.findLibrary("core-ktx").get())

    add("implementation", libs.findLibrary("coroutines").get())
    add("implementation", libs.findLibrary("coroutines-android").get())
    add("implementation", libs.findLibrary("kotlinx-datetime").get())

    val composeBom = libs.findLibrary("androidx-compose-bom").get()
    add("implementation", platform(composeBom))
    add("androidTestImplementation", platform(composeBom))

    add("implementation", libs.findLibrary("androidx-compose-material3").get())
    add("implementation", libs.findLibrary("androidx-compose-preview").get())
    add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())

    add("testImplementation", libs.findLibrary("junit").get())
    add("androidTestImplementation", libs.findLibrary("androidx-test-junit").get())
    add("androidTestImplementation", libs.findLibrary("androidx-test-runner").get())
}
