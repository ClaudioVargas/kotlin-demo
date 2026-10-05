package com.example.androiddemo.network

/**
 * Modelo de un "post" de JSONPlaceholder (API pública de prueba).
 * Gson mapea automáticamente el JSON a esta data class.
 */
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)