package com.silencer

import android.util.Log

object Engine {
    private val TAG = "Engine"

    fun muteDevice() {
        Log.d(TAG, "mute")
    }

    fun unmuteDevice() {
        Log.d(TAG, "unmute")
    }
}