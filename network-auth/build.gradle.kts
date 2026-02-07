plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.storage)
            implementation(projects.time)
            implementation(projects.sneakNetwork)
            implementation(projects.session)

            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization)
            implementation(libs.kotlinx.serialization.runtime.json)
            implementation(libs.okhttp)
            implementation(libs.eithernet.core)
        }
    }
}
