package com.example.frd.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart(
    val product: ArrayList<Product> = ArrayList(),
    val userSession: String = "",
    val validation: Boolean = false,
    val productId: String = "",
    val paymentMethod: String = "",
    val quantity: Int = 0,
    val total: Long = 0,
) : Parcelable

data class Cart2(
    val userSession: String = "",
    val validation: Boolean = false,
    val productId: String = "",
    val paymentMethod: String = "",
    val quantity: Int = 0,
    val total: Long = 0,
    val listProduct: ArrayList<Product> = ArrayList()
)