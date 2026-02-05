plugins {
    id("ninja.bryansills.multiplatform.library.compose")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project.dependencies.platform(libs.androidx.compose.bom))
                implementation(libs.androidx.compose.ui)
                implementation(libs.androidx.compose.material3)
            }
        }
    }
}
