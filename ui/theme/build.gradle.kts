plugins {
  alias(libs.plugins.loudping.multiplatform.compose)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(project.dependencies.platform(libs.compose.bom))
      api(libs.compose.material3)
      api(libs.compose.runtime)

      implementation(libs.androidx.annotation)
      implementation(libs.compose.ui)
      implementation(libs.compose.foundation)
      implementation(libs.compose.ui.graphics)
      implementation(libs.compose.ui.text)
    }
  }
}
