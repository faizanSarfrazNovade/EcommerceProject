package com.example.frd.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Order(
    var id: String = "",
    val orderNumber : Int = 0,
    val status : String = "0",
    val orderDate: Date = Date(0),
    val user: User = User(),
    val total: Double = 0.0,
    val cart : Cart = Cart(),
    val deliveryAddress : DeliveryAddress = DeliveryAddress(),
) : Parcelable