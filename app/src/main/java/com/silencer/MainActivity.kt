package com.silencer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import kotlin.math.sign

class MainActivity : AppCompatActivity() {
    companion object {
        private val KEYSTATE_MENU = 1
        private val KEYSTATE_VOLUP = 2
    }

    private var keyPressState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val params = window.attributes
        params.screenBrightness = 0f
        window.attributes = params

//        setContentView(R.layout.activity_main)
    }

    private fun checkKeyCombination(): Boolean {
        if (keyPressState == KEYSTATE_MENU.or(KEYSTATE_VOLUP)) {

            return true
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_MENU -> {
                keyPressState = keyPressState.or(KEYSTATE_MENU)
                return checkKeyCombination()
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                keyPressState = keyPressState.or(KEYSTATE_VOLUP)
                return checkKeyCombination()
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
