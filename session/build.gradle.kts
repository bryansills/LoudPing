plugins {
    alias(libs.plugins.loudping.jvm)
}

dependencies {
  implementation(projects.storage)
  implementation(libs.coroutines)
}
