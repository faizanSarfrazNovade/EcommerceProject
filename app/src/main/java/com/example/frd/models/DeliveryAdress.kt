package com.example.frd.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeliveryAddress(
    var id: String = "",
    val nameCustomer: String = "",
    val country: String = "France",
    val street: String = "",
    val postalCode : Int = 0,
    val number : Int = 0
) : Parcelable