package com.example.restaurent.screen

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.restaurant.model.ImageWithText
import com.example.restaurent.MainActivity
import com.example.restaurent.R
import com.example.restaurent.ScreenNavigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(navController: NavController,){
    var selectedTabIntex by remember {
        mutableStateOf(0)
    }
    var selectedname by remember {
        mutableStateOf("")
    }
    var temail by remember {
        mutableStateOf("")
    }
    var tcountry by remember {
        mutableStateOf("")
    }
    var tmobile by remember {
        mutableStateOf("")
    }
    var tarea by remember {
        mutableStateOf("")
    }

    val firestore = FirebaseFirestore.getInstance()
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference = database.getReference("users")
        reference.child(Firebase.auth.uid.toString()).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val dataSnapshot: DataSnapshot  = it.result
                    //name =dataSnapshot.child("name").value.toString()
                    selectedname =dataSnapshot.child("name").value.toString()
                    temail =dataSnapshot.child("email").value.toString()
                    tmobile =dataSnapshot.child("mobile").value.toString()
                    tcountry =dataSnapshot.child("country").value.toString()
                    tarea =dataSnapshot.child("address").value.toString()



                }
            }
        Log.d("name",selectedname)

        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(name = "Profile",modifier = Modifier.padding(16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            ProfileSection()
            Spacer(modifier = Modifier.height(25.dp))

            Column(
                // val letterSpacing = 0.5.sp
                //    val lineHeight = 20.sp
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = selectedname,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                    lineHeight = 20.sp
                )
                Text(
                    text = tarea,
                    letterSpacing = 0.5.sp,
                    lineHeight = 20.sp
                )


            }
            Spacer(Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){

                ActionButton(
                    text = "Mobile :",

                    modifier = Modifier
                        .defaultMinSize(95.dp)
                        .height(30.dp)
                )

                ActionButton(
                    text = tmobile,
                    modifier = Modifier
                        .defaultMinSize(95.dp)
                        .height(30.dp)
                )

            }
            Spacer(Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){

                ActionButton(
                    text = "Email :",

                    modifier = Modifier
                        .defaultMinSize(95.dp)
                        .height(30.dp)
                )

                ActionButton(
                    text = temail,
                    modifier = Modifier
                        .defaultMinSize(95.dp)
                        .height(30.dp)
                )

            }
            Spacer(Modifier.size(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){

                ActionButton(
                    text = "Country :",

                    modifier = Modifier
                        .defaultMinSize(95.dp)
                        .height(30.dp)
                )

                ActionButton(
                    text = tcountry,
                    modifier = Modifier
                        .defaultMinSize(95.dp)
                        .height(30.dp)
                )

            }
            //Text(text = tarea)

            Spacer(Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){
                ActionButton(
                    text = "LogOut",
                    modifier = Modifier
                        .defaultMinSize(95.dp)
                        .height(30.dp)
                        .clickable {
                            Firebase.auth.signOut()


                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            activity?.finish()

                        }
                )
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


//    firestore.collection("users").document(Firebase.auth.uid.toString()).get().addOnCompleteListener {
//        if(it.isSuccessful){
//            name1 = it.result["name"].toString()
//        }
//
//    }

}

@Composable
fun TopBar(
    name: String,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
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

        Icon(
            painter = painterResource(id =R.drawable.ic_dotmenu),
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ProfileSection(){
    Column(Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            RoundImage(
                image = painterResource(id = R.drawable.food),
                modifier = Modifier
                    .size(100.dp)
                    .weight(3f)
            )

        }
        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
){
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}
//  verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceAround,





@Composable
fun ProfileDescription(
    displayName: String,
    description: String,


    ){
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp

}


@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: ImageVector? = null
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(6.dp)
    ) {
        if(text != null){
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        if (icon != null){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}



