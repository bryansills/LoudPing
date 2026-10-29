plugins {
  alias(libs.plugins.loudping.root)
}

dependencyAnalysis {
  useTypesafeProjectAccessors(true)
  issues {
    all { onAny { severity("fail") } }
    project(":app-theme") {
      onUnusedDependencies { severity("ignore") }
      onUsedTransitiveDependencies { severity("ignore") }
    }
    project(":database:core") {
      onUsedTransitiveDependencies { severity("ignore") }
    }
  }
}
