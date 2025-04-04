plugins {
    id("ninja.bryansills.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
        }
        commonTest.dependencies {
            implementation(projects.core.modelTest)
            implementation(libs.turbine)
        }
    }
}
