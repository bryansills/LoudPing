plugins {
    id("ninja.bryansills.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            api(projects.albumGrouper)
            implementation(projects.albumRepo)
        }
    }
}
