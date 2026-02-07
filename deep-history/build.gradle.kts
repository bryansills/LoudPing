plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            implementation(projects.database)
            implementation(projects.trackRepo)
            implementation(libs.kotlinx.serialization.runtime.json)
            api(libs.kotlinx.datetime)
        }
    }
}
