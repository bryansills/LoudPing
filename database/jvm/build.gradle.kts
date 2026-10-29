plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(libs.sqldelight.runtime)
  implementation(projects.database.core)
  implementation(libs.sqldelight.jvm.driver)
  implementation(libs.sqldelight.async)
}
