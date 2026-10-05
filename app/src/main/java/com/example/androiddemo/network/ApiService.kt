package com.example.androiddemo.network

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Definición de los endpoints REST. Se consume desde RetrofitClient.
 */
interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post
}