package com.example.testforegroundservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class VolumeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.media.VOLUME_CHANGED_ACTION") {
            val volume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1)
            if (volume != -1) {
                Toast.makeText(context, "Volume Alterado! Valor Atual: $volume", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

