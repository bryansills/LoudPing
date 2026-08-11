plugins {
    alias(libs.plugins.loudping.root)
}

dependencyAnalysis {
    // TODO: fix the problems identified by this library
    // issues { all { onAny { severity("fail") } } }
}
