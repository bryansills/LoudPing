plugins {
    id("ninja.bryansills.sqldelight")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            api(libs.paging)
            implementation(libs.paging.sqldelight)
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
            packageName = "ninja.bryansills.loudping.database"
            schemaOutputDirectory = file("src/main/sqldelight/databases")
            verifyMigrations = true
            generateAsync = true
            deriveSchemaFromMigrations = true
            dialect(libs.sqldelight.sqlite.dialect)
        }
    }
}
