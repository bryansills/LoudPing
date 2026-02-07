plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
        }
    }
}
