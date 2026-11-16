package ninja.bryansills.loudping.app.core.navigation

// @Composable
// fun DarkModeStatusBarDisposableEffect(
//  navController: NavController,
//  view: View = LocalView.current,
// ) {
//  DisposableEffect(navController, view) {
//    val window: Window = (view.context as Activity).window
//    val insetsController = WindowCompat.getInsetsController(window, view)
//
//    val listener = NavController.OnDestinationChangedListener { _, destination, arguments ->
//      val shouldHaveDarkStatusBar = arguments?.getBoolean(DarkModeStatusBarNavArg) ?: false
//      val hasLightStatusBar = insetsController.isAppearanceLightStatusBars
//
//      if (hasLightStatusBar && shouldHaveDarkStatusBar) {
//        insetsController.isAppearanceLightStatusBars = false
//      }
//      if (!hasLightStatusBar && !shouldHaveDarkStatusBar) {
//        insetsController.isAppearanceLightStatusBars = true
//      }
//    }
//    navController.addOnDestinationChangedListener(listener)
//
//    onDispose { navController.removeOnDestinationChangedListener(listener) }
//  }
// }
//
// val DarkModeStatusBarNavArg = "hasDarkModeStatusBar"
