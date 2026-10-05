package com.example.androiddemo.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androiddemo.R
import com.example.androiddemo.databinding.ActivityNotificationBinding
import com.example.androiddemo.util.NotificationHelper

/**
 * Demo 8 — Notificaciones
 * Crea un canal y muestra una notificación (permiso runtime en Android 13+).
 */
class DemoNotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                sendNotification()
            } else {
                Toast.makeText(this, R.string.notification_permission_denied, Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.notification_title)

        binding.buttonSendNotification.setOnClickListener { checkPermissionAndSend() }
    }

    private fun checkPermissionAndSend() {
        // Android 13 (API 33) y superior requieren POST_NOTIFICATIONS en runtime
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            sendNotification()
        }
    }

    private fun sendNotification() {
        NotificationHelper.showNotification(this)
        Toast.makeText(this, R.string.notification_sent, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}