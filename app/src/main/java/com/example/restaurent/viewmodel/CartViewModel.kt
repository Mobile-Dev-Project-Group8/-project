package com.example.restaurent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurent.repo.RestaurentRepo
import com.example.restaurent.util.RecipeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartViewModel(val recipesRepo: RestaurentRepo): ViewModel() {
    val recipesStateFlow = MutableStateFlow<RecipeResponse?>(null)
    init {
        viewModelScope.launch {
            recipesRepo.getCartItem().collect {
                recipesStateFlow.value = it
            }
        }
    }
}