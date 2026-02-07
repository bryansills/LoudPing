plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            implementation(libs.turbine)
            implementation(libs.coroutines.test)
        }
    }
}
