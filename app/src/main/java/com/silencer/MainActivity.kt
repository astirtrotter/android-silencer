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
            return true
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (Engine.isSilenced) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    keyPressState = keyPressState.or(KEYSTATE_VOLDN)
                    if (checkKeyCombination()) {
                        Engine.unsilence(this)
                        return true
                    }
                }
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    keyPressState = keyPressState.or(KEYSTATE_VOLUP)
                    if (checkKeyCombination()) {
                        Engine.unsilence(this)
                        return true
                    }
                }
            }
        }

        return Engine.isSilenced || super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (Engine.isSilenced) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    keyPressState = keyPressState.and(KEYSTATE_VOLDN.inv())
                }
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    keyPressState = keyPressState.and(KEYSTATE_VOLUP.inv())
                }
            }
        }

        return Engine.isSilenced || super.onKeyUp(keyCode, event)
    }

    override fun onResume() {
        if (!isInitial && Engine.isSilenced) {
            Engine.isSilenced = false

            // time limit
            Handler().postDelayed({
                Engine.silence(this@MainActivity)
            }, 2000)
        } else if (isInitial) {
            isInitial = false
        }
        super.onResume()
    }
}
