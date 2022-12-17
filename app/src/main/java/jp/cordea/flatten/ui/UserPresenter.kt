package jp.cordea.flatten.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import jp.cordea.flatten.repository.UserRepository

sealed class UserModel {
    class Loaded(
        val name: String,
        val thumbnail: String,
    ) : UserModel()

    object Loading : UserModel()
}

@Composable
fun userPresenter(
    userRepository: UserRepository
): UserModel {
    val userState by userRepository.find(forceRefresh = false).collectAsState(initial = null)
    return userState?.let { user ->
        UserModel.Loaded(
            user.name,
            user.picture,
        )
    } ?: UserModel.Loading
}
