plugins {
    id("ninja.bryansills.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sneak)
        }
    }
}
