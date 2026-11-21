package ninja.bryansills.loudping.gradle.util

import com.github.gmazzo.buildconfig.BuildConfigExtension

fun BuildConfigExtension.addString(key: String, value: String) {
  this.buildConfigField("String", key, "\"$value\"")
}
