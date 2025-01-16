package com.example.testforegroundservices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class VolumeService : Service() {

    override fun onCreate() {
        super.onCreate()

        // Inicia o serviço em primeiro plano
        startForeground(1, createNotification())
    }

    private fun createNotification(): Notification {
        val channelId = "foreground_service_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Se o canal de notificações ainda não foi criado, cria o canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Foreground Service Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Cria uma notificação que será exibida enquanto o serviço estiver rodando
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Volume Service")
            .setContentText("O serviço está rodando em segundo plano")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Garantir que o serviço seja executado até que o app seja fechado explicitamente
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}


