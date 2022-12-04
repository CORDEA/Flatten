package jp.cordea.flatten.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun User() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "User")
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

        }
    }
}
