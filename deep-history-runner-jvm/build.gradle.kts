plugins {
  alias(libs.plugins.loudping.jvm)
  application
}

application {
  mainClass = "ninja.bryansills.loudping.deephistory.runner.MainKt"
}

dependencies {
  implementation(projects.core.coroutinesExt)
  implementation(projects.core.model)
  implementation(projects.deepHistoryDash)
  implementation(projects.deepHistory)
  implementation(projects.database.core)
  implementation(libs.coroutines)
}
