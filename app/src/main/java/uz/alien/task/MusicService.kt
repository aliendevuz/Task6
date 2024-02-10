package uz.alien.task

import android.app.IntentService
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import android.widget.Toast

class MusicService : IntentService("MusicIntentService") {

    lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Started Intent Service", Toast.LENGTH_SHORT).show()
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
    }
    override fun onHandleIntent(intent: Intent?) {
        player.start()
        Thread.sleep(player.duration.toLong())
    }

    override fun onDestroy() {
        player.stop()
        player.release()
        super.onDestroy()
        Toast.makeText(this, "Destroyed Intent Service", Toast.LENGTH_SHORT).show()
    }
}