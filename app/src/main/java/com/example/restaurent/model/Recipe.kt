package com.example.restaurent.model

data class Recipe (
//    val title: String? = null,
//    val ratting: String? = null,
//    val featuredImage: String? = null,
//    val description: String? = null,
    val name: String, val des: String, val imageURL: String,val price: String



){
    constructor() : this("","","","")
}

//val name: String, val author: String, val image: String,
//    val description: String
//) {
//    constructor() : this("", "", "", "")
//}