package uz.alien.task

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import android.widget.Toast

class ServiceMedia : Service() {

    lateinit var player: MediaPlayer

    override fun onBind(intent: Intent?) = null

    override fun onCreate() {
        Toast.makeText(this, "ServiceMedia is created!", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Toast.makeText(this, "Media player started!", Toast.LENGTH_SHORT).show()

        player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)
        player.isLooping = true
        player.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        Toast.makeText(this, "Media player is stopped!", Toast.LENGTH_SHORT).show()
    }
}