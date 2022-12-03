package jp.cordea.flatten.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import jp.cordea.flatten.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val client: HttpClient
) {
    fun find(): Flow<User> = flow {
        emit(
            client.get {
                url {
                    appendPathSegments("me")
                }
            }.body()
        )
    }
}
