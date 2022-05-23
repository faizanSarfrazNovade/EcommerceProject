package com.example.levelx.api

import com.example.levelx.models.User
import com.example.levelx.models.UserSignup
import com.example.levelx.models.UserSignin
import com.example.levelx.models.Product
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //products

    @GET("/products")
    suspend fun getProducts(): Response<MutableList<Product>?>

    @GET("/api/products/id/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product?>

    //users
    @Headers("Accept: application/json")
    @POST("/api/user/signup")
    suspend fun signUp(@Body customer: UserSignup): Response<User>

    @Headers("Accept: application/json")
    @POST("/api/user/signin")
    suspend fun signIn(@Body customer: UserSignin): Response<String>
}