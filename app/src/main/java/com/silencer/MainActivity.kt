package com.silencer

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private val KEYSTATE_VOLDN = 1
        private val KEYSTATE_VOLUP = 2
    }

    private var keyPressState = 0
    private var isInitial = true
    private var isAppFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main)

        Engine.silence(this)
    }

    private fun checkKeyCombination(): Boolean {
        if (keyPressState == KEYSTATE_VOLDN.or(KEYSTATE_VOLUP)) {
            Engine.unsilence(this)
            isAppFinished = true
            return true
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                keyPressState = keyPressState.or(KEYSTATE_VOLDN)
                if (checkKeyCombination()) {
                    return true
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                keyPressState = keyPressState.or(KEYSTATE_VOLUP)
                if (checkKeyCombination()) {
                    return true
                }
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                keyPressState = keyPressState.and(KEYSTATE_VOLDN.inv())
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                keyPressState = keyPressState.and(KEYSTATE_VOLUP.inv())
            }
        }

        return super.onKeyUp(keyCode, event)
    }

    override fun onResume() {
        if (!isInitial && Engine.isSilenced) {
            Engine.isSilenced = false

            // time limit
            Handler().postDelayed({
                if (!isAppFinished) Engine.silence(this@MainActivity)
            }, 2000)
        } else if (isInitial) {
            isInitial = false
        }
        super.onResume()
    }
}
