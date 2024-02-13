package uz.alien.task

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if ("android.provider.Telephony.SMS_RECEIVED" == intent.action) {
            Toast.makeText(context, "SMS keldi!", Toast.LENGTH_SHORT).show()
        }
    }
}