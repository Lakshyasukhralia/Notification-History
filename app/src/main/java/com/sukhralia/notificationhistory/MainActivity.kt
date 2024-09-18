package com.sukhralia.notificationhistory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sukhralia.notificationhistory.feature.tracker.presentation.history.HistoryScreen
import com.sukhralia.notificationhistory.feature.tracker.presentation.home.HomeScreen
import com.sukhralia.notificationhistory.feature.tracker.presentation.menu.MenuScreen
import com.sukhralia.notificationhistory.feature.tracker.presentation.setting.SettingScreen
import com.sukhralia.notificationhistory.ui.theme.NotificationHistoryTheme
import com.sukhralia.notificationhistory.util.ViewHelper
import com.sukhralia.notificationhistory.util.getInstalledApps
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewHelper.appList = getInstalledApps(this).sortedBy { it.appName }

        setContent {
            val navController = rememberNavController()
            NotificationHistoryTheme {

                Scaffold(
                    containerColor = Color.White,
                    bottomBar = {
                        BottomBar(
                            navController = navController
                        )
                    }
                ) { padding ->
                    Box(
                        Modifier
                            .background(Color.White)
                            .padding(padding)
                    ) {
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
                            composable<Settings>(
                                enterTransition = ::slideInToLeft,
                                exitTransition = ::slideOutToLeft,
                                popEnterTransition = ::slideInToRight,
                                popExitTransition = ::slideOutToRight
                            ) {
                                SettingScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class TopLevelDestinations(
    val route: Any,
    val title: String,
    val icon: ImageVector
) {
    MenuScreen(
        route = Menu,
        title = "Home",
        icon = Icons.Outlined.Home
    ),
    SettingScreen(
        route = Settings,
        title = "Settings",
        icon = Icons.Outlined.Settings
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination

    val showBottomNav = TopLevelDestinations.entries.map { it.route::class }.any { route ->
        currentDestination?.hierarchy?.any {
            it.hasRoute(route)
        } == true
    }

    AnimatedVisibility(visible = showBottomNav) {
        BottomAppBar(
            modifier = Modifier
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(
                        24.dp,
                        24.dp,
                        0.dp,
                        0.dp
                    ),
                    clip = false
                ),
            containerColor = Color.White,
        ) {
            TopLevelDestinations.entries.map { bottomNavigationItem ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.hasRoute(bottomNavigationItem.route::class) } == true

                if (currentDestination != null) {
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(bottomNavigationItem.route)
                        },
                        icon = {
                            Icon(
                                imageVector = bottomNavigationItem.icon,
                                contentDescription = bottomNavigationItem.title,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        alwaysShowLabel = false,
//                        label = {
//                            Text(bottomNavigationItem.title)
//                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedTextColor = Color.Gray, selectedTextColor = Color.White,
                            selectedIconColor = Color.White, unselectedIconColor = Color.Gray,
                            indicatorColor = Color.LightGray
                        )
                    )
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

@Serializable
object Settings