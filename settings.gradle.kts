pluginManagement {
  includeBuild("build-logic")
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

plugins {
  val androidGradlePluginVersion = "9.5.1"
  val kotlinGradlePluginVersion = "2.4.21"

  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("com.autonomousapps.build-health") version "3.20.4"
  id("com.autonomousapps.dedebug") version "0.1"
  id("com.android.application") version androidGradlePluginVersion apply false
  id("com.android.library") version androidGradlePluginVersion apply false
  id("com.android.kotlin.multiplatform.library") version androidGradlePluginVersion apply false
  id("org.jetbrains.compose") version "1.12.2" apply false
  id("org.jetbrains.kotlin.jvm") version kotlinGradlePluginVersion apply false
  id("org.jetbrains.kotlin.multiplatform") version kotlinGradlePluginVersion apply false
  id("org.jetbrains.kotlin.plugin.serialization") version kotlinGradlePluginVersion apply false
  id("org.jetbrains.kotlin.plugin.compose") version kotlinGradlePluginVersion apply false
  id("org.jetbrains.kotlin.plugin.parcelize") version kotlinGradlePluginVersion apply false
  id("dev.zacsweers.metro") version "1.5.2" apply false
  id("com.dropbox.dependency-guard") version "0.5.0" apply false
  id("com.github.gmazzo.buildconfig") version "6.1.2" apply false
  id("app.cash.sqldelight") version "2.4.1" apply false
  id("com.bugsnag.gradle") version "1.2.1" apply false
  id("app.cash.burst") version "2.14.0" apply false
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
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
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "LoudPing"

include(
  ":album-grouper",
  ":album-repo",
  ":app",
  ":app-root",
  ":core:coroutines-ext",
  ":core:di",
  ":core:model",
  ":core:model-test",
  ":core:sneak",
  ":data:foreman-metro",
  ":data:network-di",
  ":database:android",
  ":database:core",
  ":database:jvm",
  ":deep-history",
  ":deep-history-dash",
  ":deep-history-runner-jvm",
  ":foreman",
  ":generate-app-secrets",
  ":history-recorder",
  ":html:builder",
  ":html:callback",
  ":html:core",
  ":html:digest",
  ":jvm-network-runner",
  ":logger",
  ":logger-bugsnag",
  ":network",
  ":network-auth",
  ":play-grader",
  ":session",
  ":storage",
  ":time",
  ":track-repo",
  ":ui:android-app-res",
  ":ui:destinations",
  ":ui:home",
  ":ui:login",
  ":ui:played-tracks",
  ":ui:refresh-token-entry",
  ":ui:settings",
  ":ui:theme",
)
