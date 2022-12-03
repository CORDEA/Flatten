package jp.cordea.flatten.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.get

@Composable
fun Home(models: Flow<HomeModel> = get()) {
    Text("Android")
}
