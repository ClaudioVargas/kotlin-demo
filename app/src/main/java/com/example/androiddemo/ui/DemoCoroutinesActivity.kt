package com.example.androiddemo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androiddemo.R
import com.example.androiddemo.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Demo 3 — Corrutinas
 * Lanza una tarea "pesada" en Dispatchers.IO y actualiza la UI en el hilo principal.
 */
class DemoCoroutinesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.coroutines_title)

        binding.buttonStart.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonStart.isEnabled = false
            binding.textViewResult.text = getString(R.string.doing_task)

            // lifecycleScope: se cancela automáticamente si la Activity se destruye
            lifecycleScope.launch {
                val result = withContext(Dispatchers.IO) { doHeavyWork() }
                binding.progressBar.visibility = View.GONE
                binding.buttonStart.isEnabled = true
                binding.textViewResult.text = result
                Toast.makeText(this@DemoCoroutinesActivity, R.string.task_finished, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** Tarea ficticia que simula trabajo en segundo plano (por ej. lectura de archivo). */
    private suspend fun doHeavyWork(): String {
        delay(3_000) // espera sin bloquear el hilo principal
        return getString(R.string.task_result)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}