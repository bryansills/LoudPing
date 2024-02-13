import ninja.bryansills.libs

plugins {
    id("ninja-bryansills-compose-android-lib")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

dependencies {
    add("implementation", libs.findLibrary("hilt-android").get())
    add("ksp", libs.findLibrary("hilt-compiler").get())
    add("implementation", libs.findLibrary("hilt-navigation-compose").get())
    add("implementation", libs.findLibrary("viewmodel").get())
}