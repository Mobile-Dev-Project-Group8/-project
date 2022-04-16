package com.example.restaurent

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.restaurent.screen.HomeScreen
import com.example.restaurent.screen.ProfileScreen
import com.example.restaurent.screen.RecipeListScreen
import com.example.restaurent.screen.SettingsScreen


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
        composable(
            route = BottomBarScreen.RList.route
        ){
            RecipeListScreen()
        }

    }
}