pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
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
include(":static-pages")
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
include(":database")
include(":history-recorder")
include(":auth-ui")
include(":app-theme")
include(":ui:home")
