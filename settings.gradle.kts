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

rootProject.name = "Loud Ping"
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
