package com.silencer

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log

object Engine {
    private val TAG = "SilencerEngine"
    private val ADMIN_RECEIVER_REQUEST_CODE = 101

    fun silence(activity: Activity) {
        turnOffScreen(activity)
        mute(activity)
    }

    fun unsilence(context: Context) {
        turnOnScreen(context)
        unmute(context)
    }


    private fun turnOffScreen(activity: Activity) {
        Log.d(TAG, "turnOff")

        val policyManager = activity.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(activity, SilencerAdminReceiver::class.java)
        if (policyManager.isAdminActive(componentName)) {
            policyManager.lockNow()
        } else {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            activity.startActivityForResult(intent, ADMIN_RECEIVER_REQUEST_CODE)
        }
    }

    private fun turnOnScreen(context: Context) {
        Log.d(TAG, "turnOn")
    }

    private fun mute(context: Context) {
        Log.d(TAG, "mute")
    }

    private fun unmute(context: Context) {
        Log.d(TAG, "unmute")
    }
}