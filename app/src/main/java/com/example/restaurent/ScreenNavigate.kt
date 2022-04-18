package com.example.restaurent

sealed class ScreenNavigate(val route: String) {
    object LoginScreen : ScreenNavigate("log_screen")
    object RegisterScreen : ScreenNavigate("register_screen")
    object MainScreen1 : ScreenNavigate("main_screen")
    object SplashScreen : ScreenNavigate("splash_screen")

}
//j