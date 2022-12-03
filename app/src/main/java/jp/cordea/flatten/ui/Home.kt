package jp.cordea.flatten.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.get

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Home(models: Flow<HomeModel> = get()) {
    val model by models.collectAsState(initial = HomeModel.Loading)
    Scaffold(
        topBar = {
            MediumTopAppBar(
                actions = {
                    when (val m = model) {
                        is HomeModel.Loaded -> IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = rememberAsyncImagePainter(m.thumbnail),
                                contentDescription = "Profile"
                            )
                        }
                        HomeModel.Loading -> {}
                    }
                },
                title = { Text(text = "Home") }
            )
        }
    ) { padding ->
        when (val m = model) {
            is HomeModel.Loaded ->
                LazyColumn(
                    modifier = Modifier.padding(padding)
                ) {
                    m.items.map {
                        item {
                            HomeItem(model = it)
                        }
                    }
                }
            HomeModel.Loading ->
                CircularProgressIndicator()
        }
    }
}

@Composable
private fun HomeItem(model: HomeItemModel) {
    Row {
        Text(
            modifier = Modifier.padding(16.dp),
            text = model.title
        )
    }
}
