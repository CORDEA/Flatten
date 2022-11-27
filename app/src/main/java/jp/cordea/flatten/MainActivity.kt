package jp.cordea.flatten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import jp.cordea.flatten.ui.Home
import jp.cordea.flatten.ui.theme.FlattenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlattenTheme {
                Home()
            }
        }
    }
}
