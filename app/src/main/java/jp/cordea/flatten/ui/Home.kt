package jp.cordea.flatten.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.ktor.http.auth.*
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.get
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
                Body(model = m, modifier = Modifier.padding(padding))
            HomeModel.Loading ->
                CircularProgressIndicator()
        }
    }
}

@Composable
private fun Body(
    model: HomeModel.Loaded,
    modifier: Modifier
) {
    val event by model.onEvent.collectAsState(initial = null)
    val context = LocalContext.current
    LaunchedEffect(event) {
        when (val e = event) {
            is HomeEvent.OpenUrl ->
                context.startActivity(Intent(Intent.ACTION_VIEW, e.url))
            null -> {}
        }
    }
    LazyColumn(
        modifier = modifier
    ) {
        model.items.map {
            item {
                HomeItem(model = it)
            }
        }
    }
}

@Composable
private fun HomeItem(model: HomeItemModel) {
    Box(modifier = Modifier.clickable {
        model.onClick()
    }) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = model.title
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = model.subtitle
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                text = DateTimeFormatter.ofPattern("M/dd").format(model.createdAt)
            )
        }
    }
}

@Preview
@Composable
fun HomeItem() {
    HomeItem(
        model = HomeItemModel(
            title = "title",
            subtitle = "subtitle",
            createdAt = LocalDateTime.now()
        ) {}
    )
}
