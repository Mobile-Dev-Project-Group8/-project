package com.example.restaurent.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurent.MainActivity
import com.example.restaurent.R
import com.example.restaurent.ScreenNavigate
import com.example.restaurent.model.Cart
import com.example.restaurent.model.Recipe
import com.example.restaurent.repo.RestaurentRepo
import com.example.restaurent.ui.theme.lightGrey
import com.example.restaurent.ui.theme.orange
import com.example.restaurent.ui.theme.titleTextColor
import com.example.restaurent.ui.theme.white
import com.example.restaurent.util.OnError
import com.example.restaurent.util.OnSuccess
import com.example.restaurent.viewmodel.CartViewModel
import com.example.restaurent.viewmodel.RestaurentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun CardScreen (

    restaurentViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(RestaurentRepo())
    )

){
    var totalItem by remember {
        mutableStateOf("")
    }
//    var totalSum by remember {
//        mutableStateOf(0)
//    }

    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current

    val firestore = FirebaseFirestore.getInstance()





    firestore.collection("cart").document(Firebase.auth.uid.toString())
        .collection("cart").get().addOnCompleteListener {
            it.result.forEach {tt->
                val price = tt.getString("price")
                if(price!=null){
                    val ppp = price.toInt()


                   // totalSum += ppp

                }





            }

        }


    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        when (val booksList = restaurentViewModel.recipesStateFlow.asStateFlow().collectAsState().value) {

            is OnError -> {
                Text(text = "Please try after sometime")
            }

            is OnSuccess -> {
                val listOfBooks = booksList.querySnapshot?.toObjects(Cart::class.java)
                totalItem =listOfBooks?.size.toString()
                if ( listOfBooks?.size!=0){
                    listOfBooks?.let {

                        Column {
                            Text(
                                text = "Cart",
                                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold),
                                modifier = Modifier.padding(16.dp)
                            )

                            LazyColumn(modifier = Modifier.height(400.dp)) {
                                items(listOfBooks) {

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {

                                        CartDetails(it)




                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(20.dp))

                            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                                Divider(color = lightGrey, thickness = 2.dp)
                                Spacer(modifier = Modifier.padding(8.dp))
                                val context = LocalContext.current
                                val activity = (LocalContext.current as? Activity)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "$totalItem Items",
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )

                                    Text(
                                        text = "€",
                                        fontSize = 18.sp,
                                        color = titleTextColor,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Button(
                                    onClick = {
                                        val intent = Intent(context, OrderPlaceActivity::class.java)
                                        context.startActivity(intent)
                                        activity?.finish()
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = orange),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 30.dp,
                                            bottom = 34.dp
                                        )
                                        .align(Alignment.CenterHorizontally),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text(
                                        text = "Next",
                                        color = white,
                                        style = MaterialTheme.typography.button,
                                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                    )
                                }

                            }



                        }
                    }

                }else{
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Click",
                            modifier = Modifier.clickable {
                                val intent = Intent(context, ListActivity::class.java)
                                context.startActivity(intent)
                                activity?.finish()

                            }
                                .background(color = Color.Yellow)
                        )

                    }



                }


            }
        }



    } else {
        Spacer(Modifier.size(16.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ActionButton(
                text = "Login",
                modifier = Modifier
                    .defaultMinSize(95.dp)
                    .height(30.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        activity?.finish()

                    }
            )
        }


    }




}

@Composable
fun CartDetails(cart: Cart) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .width(120.dp)
                .height(130.dp)
                .padding(10.dp)
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                ),
            imageModel = cart.imageURL,
            contentDescription = "",
            contentScale = ContentScale.Inside,

            placeHolder = ImageBitmap.imageResource(R.drawable.picture),
            error = ImageBitmap.imageResource(R.drawable.picture)

        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = cart.title,
                fontSize = 18.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                orange,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("€")
                        }
                        withStyle(
                            style = SpanStyle(
                                titleTextColor
                            )
                        ) {
                            append(cart.price)
                        }
                    },
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier,
                    fontSize = 16.sp

                )
                Box(
                    modifier = Modifier
                        .size(35.dp, 35.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "x1",
                        fontSize = 14.sp,
                        color = titleTextColor
                    )
                }
            }

        }
    }

}


@Composable
fun NextButtonWithTotalItems() {
}

class CartViewModelFactory(private val restaurentRepo: RestaurentRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(restaurentRepo) as T
        }

        throw IllegalStateException()
    }

}