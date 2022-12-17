package jp.cordea.flatten.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

const val TAG_HOME = "home"
const val TAG_USER = "user"

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = TAG_HOME) {
        composable(route = TAG_HOME) {
            Home(navController)
        }
        composable(route = TAG_USER) {
            User(navController)
        }
    }
}
