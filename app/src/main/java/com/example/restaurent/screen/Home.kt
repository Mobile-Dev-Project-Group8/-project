package com.example.restaurent.screen


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.restaurent.BottomBarScreen
import com.example.restaurent.ItemDetails
import com.example.restaurent.R
import com.example.restaurent.ScreenNavigate


@Composable
fun HomeScreen(navController: NavController) {


    val context = LocalContext.current
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFEFA))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HomeTopBar(name = "Home",modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            elevation = 4.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.restaurent), contentDescription = null)
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),

                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column() {
                        Text("Restaurant", fontWeight = FontWeight.W700)
                        Text("Oulu, Finland", fontWeight = FontWeight.W300)

                    }


                }


            }
        }

        Card(
            elevation = 4.dp,
            modifier = Modifier.height(260.dp)
        ) {

            Image(painter = painterResource(id = R.drawable.menu), contentDescription = null)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),

                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .border(1.dp, Color.Red, RectangleShape)
                            .fillMaxWidth()
                            .padding(20.dp)) {
                        TextButton(onClick = {


                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)) {
                            Text(text = "Menu", color = Color.Black)
                        }
                        TextButton(onClick = {

                            //addd
                            navController.navigate(route = BottomBarScreen.RList.route )


                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)) {
                            Text(text = "Click", color = Color.Black)
                        }

                    }


                }


            }
        }
    }
}

@Composable
fun MainContent(navController1: NavHostController){
    var selectedTabIntex by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HomeTopBar(name = "Home",modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(4.dp))
        HomeSection(navController1)


    }



}

@Composable
fun HomeTopBar(
    name: String,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        val context = LocalContext.current
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Image(
            painter = painterResource(id = R.drawable.cart),
            contentDescription = "Back",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
                .clickable {
                    val intent = Intent(context, CartActivity::class.java)
                    context.startActivity(intent)
                }
        )
    }
}


@Composable
fun HomeSection(navController:NavHostController){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFEFA))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            elevation = 4.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.restaurent), contentDescription = null)
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),

                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column() {
                        Text("Restaurant", fontWeight = FontWeight.W700)
                        Text("Oulu, Finland", fontWeight = FontWeight.W300)

                    }


                }


            }
        }

        Card(
            elevation = 4.dp,
            modifier = Modifier.height(260.dp)
        ) {

            Image(painter = painterResource(id = R.drawable.menu), contentDescription = null)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),

                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .border(1.dp, Color.Red, RectangleShape)
                            .fillMaxWidth()
                            .padding(20.dp)) {
                        TextButton(onClick = {


                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)) {
                            Text(text = "Menu", color = Color.Black)
                        }
                        TextButton(onClick = {

                            //addd
                            // navController.navigate(route = ScreenNavigate.RList.route )


                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)) {
                            Text(text = "Click", color = Color.Black)
                        }

                    }


                }


            }
        }
    }
}


