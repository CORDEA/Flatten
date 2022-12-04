package jp.cordea.flatten.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.*
import jp.cordea.flatten.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val client: HttpClient
) {
    private var user: User? = null

    fun find(forceRefresh: Boolean): Flow<User> = flow {
        val cache = user
        if (cache != null && !forceRefresh) {
            emit(cache)
            return@flow
        }
        try {
            val response = client.get {
                url {
                    appendPathSegments("me")
                }
            }.body<User>()
            user = response
            emit(response)
        } catch (e: JsonConvertException) {
            user = null
            throw e
        }
    }
}
