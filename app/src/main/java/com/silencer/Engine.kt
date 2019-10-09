package com.silencer

import android.content.Context
import android.util.Log

object Engine {
    private val TAG = "SilencerEngine"

    fun silence(context: Context) {
        turnOffScreen(context)
        mute(context)
    }

    fun unsilence(context: Context) {
        turnOnScreen(context)
        unmute(context)
    }


    private fun turnOffScreen(context: Context) {
        Log.d(TAG, "turnOff")
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