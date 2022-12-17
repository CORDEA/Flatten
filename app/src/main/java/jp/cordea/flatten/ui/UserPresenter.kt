package jp.cordea.flatten.ui

import androidx.compose.runtime.*
import jp.cordea.flatten.repository.UserRepository

sealed class UserModel {
    class Loaded(
        val name: String,
        val thumbnail: String,
        val onClickBack: () -> Unit,
        val uiEvent: UserUiEvent
    ) : UserModel()

    object Loading : UserModel()
}

sealed class UserUiEvent {
    object Back : UserUiEvent()
    object Empty : UserUiEvent()
}

@Composable
fun userPresenter(
    userRepository: UserRepository
): UserModel {
    var event: UserUiEvent by remember { mutableStateOf(UserUiEvent.Empty) }
    val userState by userRepository.find(forceRefresh = false).collectAsState(initial = null)
    return userState?.let { user ->
        UserModel.Loaded(
            user.name,
            user.picture,
            { event = UserUiEvent.Back },
            event
        )
    } ?: UserModel.Loading
}
