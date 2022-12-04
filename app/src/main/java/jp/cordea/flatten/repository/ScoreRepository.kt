package jp.cordea.flatten.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.*
import jp.cordea.flatten.data.Score
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ScoreRepository(
    private val client: HttpClient
) {
    private var scores: List<Score> = emptyList()

    fun findLikes(id: String, forceRefresh: Boolean): Flow<List<Score>> = flow {
        val cache = scores
        if (cache.isNotEmpty() && !forceRefresh) {
            emit(cache)
            return@flow
        }
        try {
            val response = client.get {
                url {
                    appendPathSegments("users/$id/likes")
                }
            }.body<List<Score>>()
            scores = response
            emit(response)
        } catch (e: JsonConvertException) {
            scores = emptyList()
            throw e
        }
    }
}
