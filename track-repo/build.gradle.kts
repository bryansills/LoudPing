plugins {
    id("ninja.bryansills.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            implementation(projects.database)
            implementation(projects.network)
        }
    }
}
