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
      classpath("org.jetbrains.kotlin:kotlin-metadata-jvm:2.4.10")
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("com.autonomousapps.build-health") version "3.19.1"
    id("com.autonomousapps.dedebug") version "0.1"
    id("com.android.application") version "9.3.2" apply false
    id("com.android.library") version "9.3.1" apply false
    id("com.android.kotlin.multiplatform.library") version "9.3.2" apply false
    id("com.github.gmazzo.buildconfig") version "6.0.10" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.10" apply false
    id("org.jetbrains.compose") version "1.12.0" apply false
    id("com.dropbox.dependency-guard") version "0.5.0" apply false
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
    id("org.jetbrains.kotlin.jvm") version "2.4.10" apply false
    id("org.jetbrains.kotlin.multiplatform") version "2.4.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.10" apply false
    id("com.google.devtools.ksp") version "2.3.11" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.4.10" apply false
    id("app.cash.sqldelight") version "2.3.2" apply false
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
include(":app")
include(":app-core")
include(":sneak")
include(":storage")
include(":session")
include(":di")
include(":network")
include(":network-auth")
include(":time")
include(":sneak-network")
include(":generate-app-secrets")
include(":android-app-res")
include(":jvm-network-runner")
include(":database:core")
include(":database:android")
include(":database:jvm")
include(":history-recorder")
include(":app-theme")
include(":ui:home")
include(":ui:destinations")
include(":ui:login")
include(":ui:settings")
include(":foreman")
include(":ui:played-tracks")
include(":ui:refresh-token-entry")
include(":logger")
include(":logger-bugsnag")
include(":deep-history")
include(":deep-history-runner-jvm")
include(":coroutines-ext")
include(":album-repo")
include(":track-repo")
include(":deep-history-dash")
include(":album-grouper")
include(":core:model")
include(":core:model-test")
include(":play-grader")
include(":html:builder")
include(":html:callback")
include(":html:core")
include(":html:digest")
