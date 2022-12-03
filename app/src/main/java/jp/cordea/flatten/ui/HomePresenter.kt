package jp.cordea.flatten.ui

import androidx.compose.runtime.Composable
import jp.cordea.flatten.repository.ScoreRepository
import jp.cordea.flatten.repository.UserRepository

sealed class HomeModel {
    object Loading : HomeModel()
}

@Composable
fun homePresenter(
    userRepository: UserRepository,
    scoreRepository: ScoreRepository
): HomeModel {
    return HomeModel.Loading
}
