plugins {
    id("ninja.bryansills.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
        }
    }
}
