plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.networkAuth)
            implementation(projects.sneakNetwork)
            implementation(projects.time)

            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization)
            implementation(libs.kotlinx.serialization.runtime.json)
            implementation(libs.okhttp)
            implementation(libs.kotlinx.datetime)
            api(libs.eithernet.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
        }
    }
}
