package com.example.restaurent.model


data class Recipe (
    val name: String, val des: String, val imageURL: String,val price: String
){
    constructor() : this("","","","")
}
