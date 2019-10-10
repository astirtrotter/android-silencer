package com.silencer

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

class PhoneStateChangeListener(private val context: Context) : PhoneStateListener() {

    override fun onCallStateChanged(state: Int, phoneNumber: String?) {
        if (state == TelephonyManager.CALL_STATE_RINGING) {
            Engine.silence(context)
        }
        super.onCallStateChanged(state, phoneNumber)
    }
}