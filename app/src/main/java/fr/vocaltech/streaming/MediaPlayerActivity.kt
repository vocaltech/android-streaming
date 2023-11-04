package fr.vocaltech.streaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.vocaltech.streaming.ui.theme.StreamingTheme

class MediaPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StreamingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  MediaPlayerSetup()
                }
            }
        }
    }
}

@Composable
fun MediaPlayerSetup(model: MediaPlayerModel = viewModel()) {
    MediaPlayerScreen(model)
}

@Composable
fun MediaPlayerScreen(model: MediaPlayerModel) {
    var btnText by remember { mutableStateOf("Start") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "isPlaying: ${model.isPLaying}"
        )

        Button(
            onClick = {
                if (!model.isPLaying) {
                    btnText = "Stop"
                    model.startPlayer()
                } else {
                    btnText = "Start"
                    model.stopPlayer()
                }
            }
        ) {
            Text(btnText)
        }
    }
}