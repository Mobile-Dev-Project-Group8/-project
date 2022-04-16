package com.example.restaurent.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurent.R
import com.example.restaurent.model.Recipe
import com.example.restaurent.repo.RestaurentRepo
import com.example.restaurent.util.OnError
import com.example.restaurent.util.OnSuccess
import com.example.restaurent.viewmodel.RestaurentViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun RecipeListScreen(
    restaurentViewModel: RestaurentViewModel = viewModel(
        factory = RestaurentViewModelFactory(RestaurentRepo())
    )
) {

    when (val booksList = restaurentViewModel.recipesStateFlow.asStateFlow().collectAsState().value) {

        is OnError -> {
            Text(text = "Please try after sometime")
        }

        is OnSuccess -> {
            val listOfBooks = booksList.querySnapshot?.toObjects(Recipe::class.java)
            listOfBooks?.let {
                Column {
                    Text(
                        text = "Recipe",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(16.dp)
                    )

                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(listOfBooks) {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                RecipeDetails(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RecipeDetails(book: Recipe) {
    var showBookDescription by remember { mutableStateOf(false) }
    val bookCoverImageSize by animateDpAsState(
        targetValue =
        if (showBookDescription) 50.dp else 80.dp
    )

    Column(modifier = Modifier.clickable {
        showBookDescription = showBookDescription.not()
    }) {
        Row(modifier = Modifier.padding(12.dp)) {
            val cc=book.imageURL.trim()

            GlideImage(
                imageModel = book.imageURL,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(bookCoverImageSize),

                placeHolder = ImageBitmap.imageResource(R.drawable.picture),
                error = ImageBitmap.imageResource(R.drawable.picture)
            )
//            Image(
//                painter = rememberImagePainter(cc),
//                contentDescription = "Recipe cover page",
//
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.size(bookCoverImageSize)
//            )


            Column {
                Text(
                    text = book.name, style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )

                Text(
                    text = book.price, style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                )
            }
        }

//        AnimatedVisibility(visible = showBookDescription) {
//            Text(
//                text = book.des, style = TextStyle(
//                    fontWeight = FontWeight.SemiBold,
//                    fontStyle = FontStyle.Italic
//                ),
//                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
//            )
//        }
    }

}


class RestaurentViewModelFactory(private val restaurentRepo: RestaurentRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurentViewModel::class.java)) {
            return RestaurentViewModel(restaurentRepo) as T
        }

        throw IllegalStateException()
    }

}