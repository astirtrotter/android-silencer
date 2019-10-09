package com.silencer

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SilencerAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Restart App", Toast.LENGTH_LONG).show()
        super.onEnabled(context, intent)
    }

    override fun onDisabled(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Device Admin permission is required", Toast.LENGTH_LONG).show()
        super.onDisabled(context, intent)
    }
}
