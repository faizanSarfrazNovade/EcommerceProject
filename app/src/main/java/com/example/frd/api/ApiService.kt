package com.example.frd.api

import com.example.frd.models.Product
import com.example.frd.models.UserSignin
import com.example.frd.models.UserSignup
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //products

    @GET("/products")
    suspend fun getProducts(): Response<MutableList<Product>?>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product?>

    //users
    @Headers("Accept: application/json")
    @POST("users/signUp")
    suspend fun signUp(@Body customer: UserSignup): Response<okhttp3.ResponseBody>

    @Headers("Accept: application/json")
    @POST("users/signIn")
    suspend fun signIn(@Body customer: UserSignin): Response<okhttp3.ResponseBody>
}