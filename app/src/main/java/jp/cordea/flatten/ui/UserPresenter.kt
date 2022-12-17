package jp.cordea.flatten.ui

import androidx.compose.runtime.*
import jp.cordea.flatten.data.User
import jp.cordea.flatten.repository.UserRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

sealed class UserModel {
    class Loaded(
        val refreshing: Boolean,
        val name: String,
        val thumbnail: String,
        val onClickBack: () -> Unit,
        val onClickRefresh: () -> Unit,
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
    val scope = rememberCoroutineScope()

    var event: UserUiEvent by remember { mutableStateOf(UserUiEvent.Empty) }
    var userState: User? by remember { mutableStateOf(null) }
    var requestRefresh by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(userState) {
        if (userState == null) {
            userRepository.find(forceRefresh = false)
                .onEach { userState = it }
                .launchIn(scope)
        }
    }
    LaunchedEffect(requestRefresh) {
        if (requestRefresh) {
            userRepository.find(forceRefresh = true)
                .onStart { refreshing = true }
                .onEach { userState = it }
                .onCompletion {
                    refreshing = false
                    requestRefresh = false
                }
                .launchIn(scope)
        }
    }
    return userState?.let { user ->
        UserModel.Loaded(
            refreshing,
            user.name,
            user.picture,
            { event = UserUiEvent.Back },
            { requestRefresh = true },
            event
        )
    } ?: UserModel.Loading
}
