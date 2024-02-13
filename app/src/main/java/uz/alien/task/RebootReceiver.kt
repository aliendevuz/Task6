package uz.alien.task

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast

class RebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Receiver Action", intent.action.toString())
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            context.startService(Intent(context, ReceiverService::class.java))
            Toast.makeText(context, "Telefon qayta ishga tushirildi!", Toast.LENGTH_LONG).show()
        }
    }
}