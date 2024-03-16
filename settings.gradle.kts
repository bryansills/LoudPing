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
include(":app-sneak")
include(":generate-app-secrets")
