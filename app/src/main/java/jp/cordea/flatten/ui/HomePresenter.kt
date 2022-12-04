package jp.cordea.flatten.ui

import android.net.Uri
import androidx.compose.runtime.*
import jp.cordea.flatten.repository.ScoreRepository
import jp.cordea.flatten.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

sealed class HomeModel {
    object Loading : HomeModel()
    class Loaded(
        val thumbnail: String,
        val onClickIcon: () -> Unit,
        val items: List<HomeItemModel>,
        val onEvent: Flow<HomeEvent>
    ) : HomeModel()
}

sealed class HomeEvent {
    object NavigateToUser : HomeEvent()
    class OpenUrl(val url: Uri) : HomeEvent()
}

class HomeItemModel(
    val title: String,
    val subtitle: String,
    val createdAt: LocalDateTime,
    val onClick: () -> Unit
)

@Composable
fun homePresenter(
    userRepository: UserRepository,
    scoreRepository: ScoreRepository
): HomeModel {
    val scope = rememberCoroutineScope()
    val flow = remember { MutableSharedFlow<HomeEvent>() }
    val userState by userRepository.find().collectAsState(initial = null)
    userState?.let { user ->
        val scores by scoreRepository.findLikes(user.id).collectAsState(initial = emptyList())
        if (scores.isEmpty()) {
            return HomeModel.Loading
        }
        return HomeModel.Loaded(
            user.picture,
            {
                scope.launch {
                    flow.emit(HomeEvent.NavigateToUser)
                }
            },
            scores.map {
                HomeItemModel(it.title, it.subtitle, it.publicationDate) {
                    scope.launch {
                        flow.emit(HomeEvent.OpenUrl(Uri.parse(it.htmlUrl)))
                    }
                }
            },
            flow
        )
    } ?: return HomeModel.Loading
}
