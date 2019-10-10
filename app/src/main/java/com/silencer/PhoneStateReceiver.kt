package com.silencer

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

class PhoneStateReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephony.listen(PhoneStateChangeListener(context), PhoneStateListener.LISTEN_CALL_STATE)
    }
}
