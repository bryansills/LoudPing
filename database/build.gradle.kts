plugins {
    id("ninja-bryansills-kmp")
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
        jvmMain.dependencies {
            implementation(libs.sqldelight.jvm.driver)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.database"
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("ninja.bryansills.loudping.database")
            generateAsync = true
            deriveSchemaFromMigrations = true
        }
    }
}
