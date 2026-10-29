pluginManagement {
  includeBuild("build-logic")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

buildscript {
  dependencies {
    // TODO: delete this whole `buildscript` block once Dagger and DAGP update their Kotlin stuff
    classpath("org.jetbrains.kotlin:kotlin-metadata-jvm:2.4.21")
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("com.autonomousapps.build-health") version "3.20.0"
  id("com.autonomousapps.dedebug") version "0.1"
  id("com.android.application") version "9.4.1" apply false
  id("com.android.library") version "9.4.1" apply false
  id("com.android.kotlin.multiplatform.library") version "9.4.1" apply false
  id("com.github.gmazzo.buildconfig") version "6.1.2" apply false
  id("org.jetbrains.kotlin.plugin.compose") version "2.4.21" apply false
  id("org.jetbrains.compose") version "1.12.2" apply false
  id("com.dropbox.dependency-guard") version "0.5.0" apply false
  id("com.google.dagger.hilt.android") version "2.60.1" apply false
  id("org.jetbrains.kotlin.jvm") version "2.4.21" apply false
  id("org.jetbrains.kotlin.multiplatform") version "2.4.21" apply false
  id("org.jetbrains.kotlin.plugin.serialization") version "2.4.21" apply false
  id("com.google.devtools.ksp") version "2.3.12" apply false
  id("org.jetbrains.kotlin.plugin.parcelize") version "2.4.21" apply false
  id("app.cash.sqldelight") version "2.4.0" apply false
  id("com.bugsnag.gradle") version "1.2.0" apply false
  id("app.cash.burst") version "2.13.0" apply false
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "LoudPing"

include(
  ":app",
  ":app-core",
  ":sneak",
  ":storage",
  ":session",
  ":di",
  ":network",
  ":network-auth",
  ":time",
  ":sneak-network",
  ":generate-app-secrets",
  ":android-app-res",
  ":jvm-network-runner",
  ":database:core",
  ":database:android",
  ":database:jvm",
  ":history-recorder",
  ":app-theme",
  ":ui:home",
  ":ui:destinations",
  ":ui:login",
  ":ui:settings",
  ":foreman",
  ":ui:played-tracks",
  ":ui:refresh-token-entry",
  ":logger",
  ":logger-bugsnag",
  ":deep-history",
  ":deep-history-runner-jvm",
  ":coroutines-ext",
  ":album-repo",
  ":track-repo",
  ":deep-history-dash",
  ":album-grouper",
  ":core:model",
  ":core:model-test",
  ":play-grader",
  ":html:builder",
  ":html:callback",
  ":html:core",
  ":html:digest",
)
