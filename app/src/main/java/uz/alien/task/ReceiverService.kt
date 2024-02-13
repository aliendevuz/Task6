package uz.alien.task

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast

class ReceiverService : Service() {

    val receiver = SMSReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter()
        filter.addAction("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(receiver, filter)
        Toast.makeText(this, "Start receiving!", Toast.LENGTH_SHORT).show()
        return START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        Toast.makeText(this, "Stop receiving!", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}