plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudchirp.session"
}

dependencies {
    implementation(project(":storage"))
    implementation(project(":di"))
}
