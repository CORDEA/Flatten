package jp.cordea.flatten.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import jp.cordea.flatten.repository.ScoreRepository
import jp.cordea.flatten.repository.UserRepository

sealed class HomeModel {
    object Loading : HomeModel()
    class Loaded(
        val thumbnail: String,
        val items: List<HomeItemModel>
    ) : HomeModel()
}

class HomeItemModel(val title: String)

@Composable
fun homePresenter(
    userRepository: UserRepository,
    scoreRepository: ScoreRepository
): HomeModel {
    val userState by userRepository.find().collectAsState(initial = null)
    userState?.let { user ->
        val scores by scoreRepository.findLikes(user.id).collectAsState(initial = emptyList())
        if (scores.isEmpty()) {
            return HomeModel.Loading
        }
        return HomeModel.Loaded(
            user.picture,
            scores.map { HomeItemModel(it.title) }
        )
    } ?: return HomeModel.Loading
}
