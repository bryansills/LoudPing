plugins {
    alias(libs.plugins.loudping.jvm)
}

dependencies {
  implementation(projects.core.model)
  implementation(projects.database.core)
  implementation(projects.network)
  implementation(libs.kotlinx.datetime)
}
