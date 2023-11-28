package com.iessanalbertomagno.dam2.a4juegos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iessanalbertomagno.dam2.a4juegos.screens.FirstGameScreen
import com.iessanalbertomagno.dam2.a4juegos.screens.FourthGameScreen
import com.iessanalbertomagno.dam2.a4juegos.screens.MainScreen
import com.iessanalbertomagno.dam2.a4juegos.screens.SecondGameScreen
import com.iessanalbertomagno.dam2.a4juegos.screens.ThirdGameScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) { MainScreen(navController) }
        composable(route = AppScreens.FirstGameScreen.route) { FirstGameScreen(navController) }
        composable(route = AppScreens.SecondGameScreen.route ) { SecondGameScreen(navController) }
        composable(route = AppScreens.ThirdGameScreen.route) { ThirdGameScreen(navController) }
        composable(route = AppScreens.FourthGameScreen.route) { FourthGameScreen(navController) }
    }
}