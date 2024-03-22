plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudping.session"
}

dependencies {
    implementation(project(":storage"))
    implementation(project(":di"))
}
