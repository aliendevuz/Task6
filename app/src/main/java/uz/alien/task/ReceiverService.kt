package uz.alien.task

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast

class ReceiverService : Service() {

    val receiver = IncomingCallReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Start receiving!", Toast.LENGTH_SHORT).show()
        val filter = IntentFilter()
        filter.addAction("android.intent.action.PHONE_STATE")
        registerReceiver(receiver, filter)
        return START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        Toast.makeText(this, "Stop receiving!", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}