plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(libs.javax.inject)
  implementation(libs.metro.runtime)
}
