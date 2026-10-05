package com.example.androiddemo.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androiddemo.R
import com.example.androiddemo.databinding.ActivityCameraBinding

/**
 * Demo 6 — Cámara
 * Solicita el permiso CAMERA en runtime y lanza ACTION_IMAGE_CAPTURE.
 * La imagen vuelve como un thumbnail en el Intent de resultado.
 */
class DemoCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    // Launcher para pedir el permiso de cámara
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                launchCamera()
            } else {
                Toast.makeText(this, R.string.camera_permission_denied, Toast.LENGTH_LONG).show()
            }
        }

    // Launcher para la app de cámara
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val thumbnail: Bitmap? = result.data?.extras?.get("data") as? Bitmap
                if (thumbnail != null) {
                    binding.imageViewPhoto.setImageBitmap(thumbnail)
                    binding.textViewPhotoHint.visibility = View.GONE
                } else {
                    Toast.makeText(this, R.string.camera_no_photo, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.camera_cancelled, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.camera_title)

        binding.buttonTakePhoto.setOnClickListener {
            if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                Toast.makeText(this, R.string.camera_not_available, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            } else {
                launchCamera()
            }
        }
    }

    private fun launchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            cameraLauncher.launch(intent)
        } else {
            Toast.makeText(this, R.string.camera_not_available, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}