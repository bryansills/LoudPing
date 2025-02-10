plugins {
    id("ninja.bryansills.multiplatform.library")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.database)
            implementation(projects.trackRepo)
            implementation(libs.kotlinx.serialization.runtime)
            api(libs.kotlinx.datetime)
        }
    }
}
