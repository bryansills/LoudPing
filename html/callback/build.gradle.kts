plugins {
  alias(libs.plugins.loudping.jvm)
  application
}

application {
  mainClass = "ninja.bryansills.loudping.html.callback.MainKt"
}

dependencies {
  implementation(projects.html.core)
  implementation(libs.kotlinx.html)
  implementation(libs.okio)
}
