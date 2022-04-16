package com.example.restaurent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurent.repo.RestaurentRepo
import com.example.restaurent.util.RecipeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class RestaurentViewModel(val recipesRepo: RestaurentRepo): ViewModel() {

    val recipesStateFlow = MutableStateFlow<RecipeResponse?>(null)

    init {
        viewModelScope.launch {
            recipesRepo.getBookDetails().collect {
                recipesStateFlow.value = it
            }
        }
    }

    fun getRecipesInfo() = recipesRepo.getBookDetails()
}