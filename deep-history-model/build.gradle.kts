plugins {
    id("ninja.bryansills.multiplatform.library")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.runtime)
            api(libs.kotlinx.datetime)
        }
    }
}
