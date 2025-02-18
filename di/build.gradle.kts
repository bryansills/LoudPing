plugins { id("ninja.bryansills.android.library.kotlin") }

android { namespace = "ninja.bryansills.loudping.di" }

dependencies { implementation(libs.javax.inject) }
