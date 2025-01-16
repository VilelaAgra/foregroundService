package com.example.testforegroundservices

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Permissão de notificações necessária para o app funcionar corretamente.", Toast.LENGTH_LONG).show()
            }
        }

        // Solicitar permissão para notificações (necessário no Android 13 ou superior)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS")
            }
        }


        // Criar o canal de notificação
        createNotificationChannel()

        // Iniciar o serviço em primeiro plano
        val serviceIntent = Intent(this, VolumeService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)

        // Exibir um simples Toast
        Toast.makeText(this, "Executando Aplicativo", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        val serviceChannel = android.app.NotificationChannel(
            "VolumeChannel",
            "Controle de Volume",
            android.app.NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(android.app.NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Parar o serviço quando a MainActivity for destruída
        stopService(Intent(this, VolumeService::class.java))
    }
}
