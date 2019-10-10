package com.silencer

import android.annotation.SuppressLint
import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.view.WindowManager

object Engine {
    private val TAG = "SilencerEngine"
    private val ADMIN_RECEIVER_REQUEST_CODE = 101

    private fun log(msg: String) = Log.d(TAG, msg)

    var isSilenced = true
        set(value) {
            log("isSilenced: $field -> $value")
            field = value
        }

    private var screenWakeLock: PowerManager.WakeLock? = null

    fun silence(activity: Activity) {
        turnOffScreen(activity)
        mute(activity)
    }

    fun unsilence(activity: Activity) {
        turnOnScreen(activity)
        unmute(activity)
    }


    @SuppressLint("InvalidWakeLockTag", "WakelockTimeout")
    private fun turnOffScreen(activity: Activity) {
        log("turnOff")

        val policyManager = activity.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(activity, SilencerAdminReceiver::class.java)
        if (policyManager.isAdminActive(componentName)) {
            val pm = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
            screenWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)
            screenWakeLock!!.acquire()
            policyManager.lockNow()
            isSilenced = false
        } else {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            activity.startActivityForResult(intent, ADMIN_RECEIVER_REQUEST_CODE)
        }
    }

    private fun turnOnScreen(activity: Activity) {
        log("turnOn")

        if (screenWakeLock?.isHeld ?: false) {
            screenWakeLock!!.release()
            screenWakeLock = null
        }

        activity.window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        }

        isSilenced = true
    }

    private fun mute(activity: Activity) {
        log("mute")
    }

    private fun unmute(activity: Activity) {
        log("unmute")
    }
}