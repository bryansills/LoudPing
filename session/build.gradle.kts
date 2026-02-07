plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.storage)
        }
    }
}
