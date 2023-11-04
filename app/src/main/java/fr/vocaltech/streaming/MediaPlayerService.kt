package fr.vocaltech.streaming

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService: Service() {
    private lateinit var player: MediaPlayer
    private val resId = R.raw.mlynn_fade_away

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, resId)
        player.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}