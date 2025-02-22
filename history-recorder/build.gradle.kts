plugins {
    id("ninja.bryansills.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(projects.database)
            implementation(projects.network)
            implementation(libs.kotlinx.datetime)
        }
    }
}
