package jp.cordea.flatten.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import jp.cordea.flatten.data.User

class UserRepository(
    private val client: HttpClient
) {
    suspend fun find(): User = client.get {
        url {
            appendPathSegments("me")
        }
    }.body()
}
