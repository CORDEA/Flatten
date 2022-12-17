package jp.cordea.flatten.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        model = m.thumbnail,
                        contentDescription = m.name,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = m.name
                    )
                }
            }
            UserModel.Loading -> CircularProgressIndicator()
        }
    }
}
