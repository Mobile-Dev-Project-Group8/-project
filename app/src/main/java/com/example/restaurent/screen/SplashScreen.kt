package com.example.restaurent.screen

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.restaurent.MainActivity
import com.example.restaurent.R
import com.example.restaurent.ScreenNavigate
import com.example.restaurent.ui.theme.Purple700
import com.example.restaurent.ui.theme.yellowColor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            navController.navigate(route = ScreenNavigate.MainScreen1.route )


        } else {
            navController.navigate(ScreenNavigate.LoginScreen.route)

        }

    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else yellowColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Image(
            painterResource(id = R.drawable.food),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),

        )
    }
}

