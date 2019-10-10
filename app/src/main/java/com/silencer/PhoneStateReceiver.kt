package com.silencer

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.telephony.TelephonyManager
import android.util.Log

class PhoneStateReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Silencer", "incoming Call")

        val stateStr = intent.extras!!.getString(TelephonyManager.EXTRA_STATE)!!
        if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Handler().postDelayed({
                Engine.silence(context)
            }, 500)
        }

//        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        telephony.listen(PhoneStateChangeListener(context), PhoneStateListener.LISTEN_CALL_STATE)
    }
}
