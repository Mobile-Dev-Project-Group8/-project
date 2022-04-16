package com.example.restaurent.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restaurent.LoginScreenViewModel
import com.example.restaurent.MainScreen
import com.example.restaurent.ScreenNavigate
import com.example.restaurent.util.LoadingState


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel()
) {

    val context = LocalContext.current
    val state by viewModel.loadingState.collectAsState()
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    Row(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
           
        ) {
            Text(text = "Welcome",  fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,)
            Text(text = "SignIn Here",  fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,)

        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
//        Row(modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.Top,
//            horizontalArrangement  =  Arrangement.SpaceEvenly){
//            Text(text = "Signin", fontSize = 30.sp,  fontWeight = FontWeight.Bold,)
//        }

        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it

            },
            isError = emailErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Enter Email*")
            },
        )
        if (emailErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                password.value = it

            },
            isError = passwordErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Enter Password*")
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
//                    Icon(
//                       // imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
//                        contentDescription = "visibility",
//                        tint = androidx.compose.ui.graphics.Color.Red
//                    )
                }
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (passwordErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = {
                when {
                    email.value.text.isEmpty() -> {
                        emailErrorState.value = true
                    }
                    password.value.text.isEmpty() -> {
                        passwordErrorState.value = true
                    }
                    else -> {
                        passwordErrorState.value = false
                        emailErrorState.value = false
                        viewModel.signInWithEmailAndPassword(email.value.text.trim(), password.value.text.trim())

                    }
                }

            },
            content = {
                Text(text = "Login", color = Color.White)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        )
        Spacer(Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {

                    navController.navigate(route = ScreenNavigate.RegisterScreen.route)
                    Toast
                        .makeText(
                            context,
                            "Register clicked",
                            Toast.LENGTH_SHORT
                        )
                        .show()

                },
                shape = CutCornerShape(10),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                modifier = Modifier.fillMaxWidth(),


                ) {
                Text(
                    text = "Register",
                    style = typography.body2.copy(color = Color.White),

                )

            }

        }

        Spacer(Modifier.size(16.dp))

        when(state.status) {
            LoadingState.Status.SUCCESS -> {
                Toast.makeText(
                    context,
                    "Welcome",
                    Toast.LENGTH_SHORT
                ).show()

                LaunchedEffect(Unit) {
                    navController.navigate(route = ScreenNavigate.MainScreen1.route )
                }
                Text(text = "Success")


            }
            LoadingState.Status.FAILED -> {
                Toast.makeText(
                    context,
                    ""+state.msg,
                    Toast.LENGTH_SHORT
                ).show()

                Text(text = state.msg ?: "Error")
            }
            else -> {}
        }


    }
}
//
@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}