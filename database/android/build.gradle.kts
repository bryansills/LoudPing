plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
}

dependencies {
  api(libs.sqldelight.runtime)
  implementation(projects.database.core)
  implementation(libs.sqldelight.android.driver)
  implementation(libs.androidx.sqlite)
  implementation(libs.sqldelight.async)
}
