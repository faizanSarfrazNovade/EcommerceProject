package com.example.frd.models

data class UserToken (
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val roles: Array<String>,
    val accessToken: String = "",
    val tokenType: String = ""
    )