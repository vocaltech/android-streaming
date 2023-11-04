package fr.vocaltech.streaming

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class MediaPlayerModel(private val application: Application): AndroidViewModel(application) {
    var isPLaying by mutableStateOf(false)

    fun startPlayer() {
        application.startService(Intent(application.applicationContext, MediaPlayerService::class.java))
        isPLaying = true
    }

    fun stopPlayer() {
        application.stopService(Intent(application.applicationContext, MediaPlayerService::class.java))
        isPLaying = false
    }
}