package com.example.pawvet_1.ui.viewmodel

sealed class BreedsUiState {
    object Loading : BreedsUiState()
    data class Success(val breeds: List<String>) : BreedsUiState()
    data class Error(val message: String) : BreedsUiState()
}
