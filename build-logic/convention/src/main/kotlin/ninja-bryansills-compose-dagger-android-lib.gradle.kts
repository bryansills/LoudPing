import ninja.bryansills.libs

plugins {
    id("ninja-bryansills-compose-android-lib")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.autonomousapps.dependency-analysis")
}

dependencies {
    add("implementation", libs.findLibrary("hilt-android").get())
    add("ksp", libs.findLibrary("hilt-compiler").get())
    add("implementation", libs.findLibrary("hilt-navigation-compose").get())
    add("implementation", libs.findLibrary("viewmodel").get())
    add("implementation", libs.findLibrary("kotlinx-serialization-runtime").get())
}
