package jp.cordea.flatten.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.get
import org.koin.core.qualifier.named

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun User(navController: NavController, models: Flow<UserModel> = get(named(TAG_USER))) {
    val model by models.collectAsState(initial = UserModel.Loading)
    when (val m = model) {
        is UserModel.Loaded -> {
            LaunchedEffect(m.uiEvent) {
                when (val event = m.uiEvent) {
                    UserUiEvent.Back ->
                        navController.popBackStack()
                    UserUiEvent.Empty -> {}
                }
            }
        }
        UserModel.Loading -> {}
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "User") },
                navigationIcon = {
                    IconButton(onClick = {
                        (model as? UserModel.Loaded)?.onClickBack?.invoke()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (val m = model) {
            is UserModel.Loaded -> Box(
                modifier = Modifier.padding(padding)
            ) {
                Body(m)
            }
            UserModel.Loading -> CircularProgressIndicator()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun Body(model: UserModel.Loaded) {
    val state = rememberPullRefreshState(
        refreshing = model.refreshing,
        onRefresh = { model.onClickRefresh() }
    )
    Box(modifier = Modifier.pullRefresh(state)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                AsyncImage(
                    model = model.thumbnail,
                    contentDescription = model.name,
                    modifier = Modifier.size(72.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = model.name
                )
            }
        }
        PullRefreshIndicator(model.refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}
