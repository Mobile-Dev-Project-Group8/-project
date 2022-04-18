package com.example.restaurent.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.restaurent.ui.theme.RestaurentTheme

class ListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurentTheme {


                // A surface container using the 'background' color from the theme
                RecipeListScreen()
            }
        }
    }
}
