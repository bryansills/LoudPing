pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

plugins {
  val androidGradlePluginVersion = "9.5.2"
  val kotlinGradlePluginVersion = "2.4.21"

  id("com.android.lint") version androidGradlePluginVersion apply false
  id("org.jetbrains.kotlin.plugin.assignment") version kotlinGradlePluginVersion apply false
  id("org.jetbrains.kotlin.jvm") version kotlinGradlePluginVersion apply false
  id("org.jetbrains.kotlin.plugin.sam.with.receiver") version kotlinGradlePluginVersion apply false
}

dependencyResolutionManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
  }
  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}

rootProject.name = "build-logic"

include(":convention")
