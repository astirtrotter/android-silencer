package com.silencer

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private val KEYSTATE_MENU = 1
        private val KEYSTATE_VOLUP = 2
    }

    private var keyPressState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Engine.silence(this)
    }

    private fun checkKeyCombination(): Boolean {
        if (keyPressState == KEYSTATE_MENU.or(KEYSTATE_VOLUP)) {
            return true
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("Silencer", "$keyCode")
        when (keyCode) {
            KeyEvent.KEYCODE_MENU -> {
                keyPressState = keyPressState.or(KEYSTATE_MENU)
                if (checkKeyCombination()) {
                    Engine.unsilence(applicationContext)
                }
                return true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                keyPressState = keyPressState.or(KEYSTATE_VOLUP)
                if (checkKeyCombination()) {
                    Engine.unsilence(applicationContext)
                }
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_MENU -> {
                keyPressState = keyPressState.and(KEYSTATE_MENU.inv())
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                keyPressState = keyPressState.and(KEYSTATE_VOLUP.inv())
            }
        }

        return super.onKeyUp(keyCode, event)
    }
}
