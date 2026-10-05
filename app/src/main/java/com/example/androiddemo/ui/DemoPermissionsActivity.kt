package com.example.androiddemo.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.databinding.ActivityPermissionsBinding

/**
 * Demo 9 — Permisos en tiempo de ejecución
 * Solicita varios permisos a la vez con RequestMultiplePermissions
 * y muestra el resultado de cada uno.
 */
class DemoPermissionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionsBinding

    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val builder = StringBuilder()
        results.forEach { (permission, granted) ->
            val shortName = permission.substringAfterLast('.')
            builder.append(shortName)
                .append(": ")
                .append(if (granted) "✅ concedido" else "❌ denegado")
                .append("\n")
        }
        binding.textViewResults.text = builder.toString().trim()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.permissions_title)

        binding.buttonRequest.setOnClickListener {
            permissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.RECORD_AUDIO
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}