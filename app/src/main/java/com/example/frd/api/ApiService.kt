package com.example.frd.api

import com.example.frd.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // products
    @GET("/products")
    suspend fun getProducts(): Response<MutableList<Product>?>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product?>


    // users
    @Headers("Accept: application/json")
    @POST("api/auth/signup")
    suspend fun signUp(@Body customer: UserSignup): Response<okhttp3.ResponseBody>

    @Headers("Accept: application/json")
    @POST("api/auth/sign-in")
    suspend fun signIn(@Body customer: UserSignin): Response<UserToken>


    // cart
    @Headers("Accept: application/json")
    @POST("carts/save")
    suspend fun submitCart(@Body cart: Cart2): Response<Cart>

    @GET("/carts/{id}")
    suspend fun getCart(@Path("id") id: String): Response<Cart2?>


    //delivery
    @Headers("Accept: application/json")
    @POST("delivery/save")
    suspend fun addAddress(@Body address: DeliveryAddress): Response<okhttp3.ResponseBody>

    @GET("/delivery/{id}")
    suspend fun getAddress(@Path("id") id:String) : Response<MutableList<DeliveryAddress>?>


    //orders
    @Headers("Accept: application/json")
    @GET("orders/createOrder/{id}")
    suspend fun submitOrder(@Path("id") id:String) : Response<okhttp3.ResponseBody>
}