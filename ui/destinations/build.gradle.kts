plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  api(libs.kotlinx.serialization.runtime.core)
}
