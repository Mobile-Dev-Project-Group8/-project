package com.example.restaurent.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.restaurent.screen.ui.theme.RestaurantTheme

class OrderPlaceActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {

                navController = rememberNavController()


                OrderPlace(navController = navController)

            }
        }
    }
}


