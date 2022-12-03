package jp.cordea.flatten.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import jp.cordea.flatten.data.Score
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ScoreRepository(
    private val client: HttpClient
) {
    fun findLikes(id: String): Flow<List<Score>> = flow {
        emit(
            client.get {
                url {
                    appendPathSegments("users/$id/likes")
                }
            }.body()
        )
    }
}
