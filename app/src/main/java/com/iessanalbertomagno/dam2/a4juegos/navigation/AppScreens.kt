package com.iessanalbertomagno.dam2.a4juegos.navigation

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens (route = "main_screen")
    object FirstGameScreen: AppScreens (route = "first_game_screen")
    object SecondGameScreen: AppScreens (route = "second_game_screen")
    object ThirdGameScreen: AppScreens (route = "third_game_screen")
    object FourthGameScreen: AppScreens (route = "fourth_game_screen")
}