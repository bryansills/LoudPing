plugins { id("ninja.bryansills.multiplatform.library.compose") }

android { namespace = "ninja.bryansills.loudping.app.theme" }

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
