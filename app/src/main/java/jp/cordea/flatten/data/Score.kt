package jp.cordea.flatten.data

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val user: User,
    val htmlUrl: String
)
