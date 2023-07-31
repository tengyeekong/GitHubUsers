package com.tengyeekong.githubusers.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tengyeekong.githubusers.ui.common.theme.ComposeTheme
import com.tengyeekong.githubusers.ui.userdetails.UserDetailsScreen
import com.tengyeekong.githubusers.ui.userlist.UserListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Custom)
        super.onCreate(savedInstanceState)

        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            val enableDarkMode = rememberSaveable { mutableStateOf(isSystemInDarkTheme) }
            ComposeTheme(enableDarkMode) {
                Surface {
                    GitHubUsersApp(enableDarkMode)
                }
            }
        }
    }
}

@Composable
private fun GitHubUsersApp(enableDarkMode: MutableState<Boolean>) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationKeys.Route.USER_LIST
    ) {
        composable(route = NavigationKeys.Route.USER_LIST) {
            UserListScreen(enableDarkMode) { username ->
                navController.navigate("${NavigationKeys.Key.USER}/$username")
            }
        }
        composable(
            route = NavigationKeys.Route.USER_DETAILS,
            arguments = listOf(navArgument(NavigationKeys.Arg.USERNAME) { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString(NavigationKeys.Arg.USERNAME) ?: return@composable
            UserDetailsScreen(username) {
                navController.navigateUp()
            }
        }
    }
}

object NavigationKeys {

    object Route {
        const val USER_LIST = "USER_LIST"
        const val USER_DETAILS = "${Key.USER}/{${Arg.USERNAME}}"
    }

    object Key {
        const val USER = "USER"
    }

    object Arg {
        const val USERNAME = "USERNAME"
    }
}