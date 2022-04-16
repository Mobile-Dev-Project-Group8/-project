package com.example.restaurent.util

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class RecipeResponse
data class OnSuccess(val querySnapshot: QuerySnapshot?): RecipeResponse()
data class OnError(val exception: FirebaseFirestoreException?): RecipeResponse()