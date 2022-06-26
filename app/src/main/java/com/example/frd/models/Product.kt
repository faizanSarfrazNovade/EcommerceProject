package com.example.frd.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val image1: String = "",
        val image2:  String = "",
        val image3:  String = "",
        val image4: String = "",
        val color: String = "",
        val price: Double = 0.0,
        val rate: Double = 0.0,
        val nbVote: Int = 0,
        val stock: Int = 0,
        var quantityOnCart: Int = 0
) : Parcelable