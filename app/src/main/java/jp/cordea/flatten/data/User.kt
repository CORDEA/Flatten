package jp.cordea.flatten.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val name: String,
    val picture: String
)
