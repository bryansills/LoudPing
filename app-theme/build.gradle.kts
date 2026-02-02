plugins {
    // TODO: go back to being KMP and CMP!
//    id("ninja.bryansills.multiplatform.library.compose")
    id("ninja.bryansills.android.library.compose.screen")
}

android {
    namespace = "ninja.bryansills.loudping.app.theme"
}

//kotlin {
//    sourceSets {
//        main {
//            dependencies {
//                implementation(project.dependencies.platform(libs.androidx.compose.bom))
//                implementation(libs.androidx.compose.ui)
//                implementation(libs.androidx.compose.material3)
//            }
//        }
//    }
//}

dependencies {
    implementation(project.dependencies.platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
}
