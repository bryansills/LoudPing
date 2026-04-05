plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(projects.database.core)
            implementation(projects.network)
            implementation(libs.kotlinx.datetime)
        }
    }
}
