package jp.cordea.flatten.ui

import android.net.Uri
import androidx.compose.runtime.*
import jp.cordea.flatten.repository.ScoreRepository
import jp.cordea.flatten.repository.UserRepository
import java.time.LocalDateTime

sealed class HomeModel {
    object Loading : HomeModel()
    class Loaded(
        val thumbnail: String,
        val onClickIcon: () -> Unit,
        val items: List<HomeItemModel>,
        val uiEvent: HomeUiEvent
    ) : HomeModel()
}

sealed class HomeUiEvent {
    object NavigateToUser : HomeUiEvent()
    class OpenUrl(val url: Uri) : HomeUiEvent()
    object Empty : HomeUiEvent()
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
    var event: HomeUiEvent by remember { mutableStateOf(HomeUiEvent.Empty) }
    val userState by userRepository.find(forceRefresh = false).collectAsState(initial = null)
    userState?.let { user ->
        val scores by scoreRepository.findLikes(user.id, forceRefresh = false)
            .collectAsState(initial = emptyList())
        if (scores.isEmpty()) {
            return HomeModel.Loading
        }
        return HomeModel.Loaded(
            user.picture,
            {
                event = HomeUiEvent.NavigateToUser
            },
            scores.map {
                HomeItemModel(it.title, it.subtitle, it.publicationDate) {
                    event = HomeUiEvent.OpenUrl(Uri.parse(it.htmlUrl))
                }
            },
            event
        )
    } ?: return HomeModel.Loading
}
