package com.example.restaurent.screen

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.restaurent.MainActivity
import com.example.restaurent.ScreenNavigate
import com.example.restaurent.ui.theme.lightGrey
import com.example.restaurent.ui.theme.orange
import com.example.restaurent.ui.theme.titleTextColor
import com.example.restaurent.ui.theme.white
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

@Composable
fun OrderPlace (navController: NavController,){

    var selectedname by remember {
        mutableStateOf("")
    }

    var tmobile by remember {
        mutableStateOf("")
    }

    var tarea by remember {
        mutableStateOf("")
    }
    val activity = (LocalContext.current as? Activity)

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference = database.getReference("users")
    reference.child(Firebase.auth.uid.toString()).get()
        .addOnCompleteListener {
            if(it.isSuccessful){
                val dataSnapshot: DataSnapshot = it.result
                //name =dataSnapshot.child("name").value.toString()
                selectedname =dataSnapshot.child("name").value.toString()
                tmobile =dataSnapshot.child("mobile").value.toString()

                tarea =dataSnapshot.child("address").value.toString()



            }
        }


    val location = remember { mutableStateOf(TextFieldValue()) }
    val locationErrorState = remember { mutableStateOf(false) }
    val pErrorState = remember { mutableStateOf(false) }

    var mExpanded by remember { mutableStateOf(false) }

    val mCities = listOf("Cash", "Bank", "Card" )

    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val context = LocalContext.current

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column() {

        Text(
            text = "Order Place",
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.padding(16.dp)
        )
        Divider(color = lightGrey, thickness = 2.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Delivering To",
                fontSize = 15.sp,
                color = Color.Red
            )

            Text(
                text = ""+selectedname,
                fontSize = 18.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.size(16.dp))
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            OutlinedTextField(
                value = location.value,
                onValueChange = {
                    if (locationErrorState.value) {
                        locationErrorState.value = false
                    }
                    location.value = it

                },
                isError = locationErrorState.value,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Enter Location")
                },
            )
            if (pErrorState.value) {
                Text(text = "Select Payment", color = Color.Red)
            }
            Spacer(Modifier.size(16.dp))



            OutlinedTextField(
                value = mSelectedText,
                onValueChange = {
                    if (pErrorState.value) {
                        pErrorState.value = false
                    }
                    mSelectedText = it },

                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->

                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Select Payment") },
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded })
                }
            )


            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = androidx.compose.ui.Modifier
                    .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
            ) {
                mCities.forEach { label ->
                    DropdownMenuItem(onClick = {
                        mSelectedText = label
                        mExpanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
            if (locationErrorState.value) {
                Text(text = "Required Location", color = Color.Red)
            }
            Spacer(Modifier.size(16.dp))

            Text(
                text = "Mobile :"+tmobile,
                fontSize = 18.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.size(16.dp))

            Button(
                onClick = {
                    when {
                        location.value.text.isEmpty() -> {
                            locationErrorState.value = true
                        }
                        mSelectedText.isEmpty() -> {
                            pErrorState.value = true
                        }

                        else -> {

                            locationErrorState.value = false
                            pErrorState.value = false
                            Toast.makeText(context, "Order place Success", Toast.LENGTH_SHORT).show()

                            val intent = Intent(context, ListActivity::class.java)
                            context.startActivity(intent)
                            activity?.finish()


                        }
                    }

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
                    text = "Order Place",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }


        }




    }

}