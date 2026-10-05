package com.example.androiddemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddemo.network.Post
import com.example.androiddemo.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados de la UI para la petición de red.
 */
sealed class UiState {
    data object Loading : UiState()
    data class Success(val posts: List<Post>) : UiState()
    data class Error(val message: String) : UiState()
}

/**
 * Descarga los posts usando Retrofit + corrutinas dentro del viewModelScope.
 * Expone un StateFlow que la UI observa.
 */
class PostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadPosts()
    }

    fun loadPosts() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val posts = RetrofitClient.apiService.getPosts()
                _uiState.value = UiState.Success(posts)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}