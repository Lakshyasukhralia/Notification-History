package com.sukhralia.notificationhistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sukhralia.notificationhistory.feature.tracker.presentation.history.HistoryScreen
import com.sukhralia.notificationhistory.feature.tracker.presentation.home.HomeScreen
import com.sukhralia.notificationhistory.feature.tracker.presentation.menu.MenuScreen
import com.sukhralia.notificationhistory.ui.theme.NotificationHistoryTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NotificationHistoryTheme {
                NavHost(navController = navController, startDestination = Menu) {
                    composable<Menu>(
                        enterTransition = ::slideInToLeft,
                        exitTransition = ::slideOutToLeft,
                        popEnterTransition = ::slideInToRight,
                        popExitTransition = ::slideOutToRight
                    ) {
                        MenuScreen { pkg, app ->
                            navController.navigate(Home(pkg, app))
                        }
                    }
                    composable<Home>(
                        enterTransition = ::slideInToLeft,
                        exitTransition = ::slideOutToLeft,
                        popEnterTransition = ::slideInToRight,
                        popExitTransition = ::slideOutToRight
                    ) {
                        val args = it.toRoute<Home>()
                        HomeScreen(args.packageName, args.appName) { title, pkg ->
                            navController.navigate(History(title, pkg))
                        }
                    }
                    composable<History>(
                        enterTransition = ::slideInToLeft,
                        exitTransition = ::slideOutToLeft,
                        popEnterTransition = ::slideInToRight,
                        popExitTransition = ::slideOutToRight
                    ) {
                        val args = it.toRoute<History>()
                        HistoryScreen(args.title, args.packageName)
                    }
                }
            }
        }
    }
}

fun slideInToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideInToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

fun slideOutToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideOutToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

@Serializable
data class Home(val packageName: String, val appName: String)

@Serializable
data class History(val title: String, val packageName: String)

@Serializable
object Menu