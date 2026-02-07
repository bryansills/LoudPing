plugins {
    alias(libs.plugins.loudping.multiplatform.plain)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
        }
        commonTest.dependencies {
            implementation(projects.core.modelTest)
            implementation(libs.turbine)
        }
    }
}
