package com.example.frd.models

data class Product(
        val id: String,
        val name: String,
        val description: String,
        val image1: String,
        val image2:  String,
        val image3:  String,
        val image4: String,
        val color: String,
        val price: Int?,
        val rate: Int,
        val nbVote: Int,
        val stock: Int?,
        val dateAdded: String,
        val quantityOnCart: Int
)