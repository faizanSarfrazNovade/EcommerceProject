package com.example.frd.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeliveryAddress(
    val country: String = "France",
    val street: String = "",
    val zipCode : Int = 0,
    val city : String = "Paris",
    val userId : String = ""
) : Parcelable