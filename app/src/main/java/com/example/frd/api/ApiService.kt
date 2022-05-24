package com.example.frd.api

import com.example.frd.models.User
import com.example.frd.models.UserSignup
import com.example.frd.models.UserSignin
import com.example.frd.models.Product
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //products

    @GET("/products")
    suspend fun getProducts(): Response<MutableList<Product>?>

    @GET("/products/id/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product?>

    //users
    @Headers("Accept: application/json")
    @POST("/api/user/signup")
    suspend fun signUp(@Body customer: UserSignup): Response<User>

    @Headers("Accept: application/json")
    @POST("/api/user/signin")
    suspend fun signIn(@Body customer: UserSignin): Response<String>
}