package com.example.androiddemo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.R
import com.example.androiddemo.databinding.ActivityUiBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Demo 1 — UI Básica
 * Widgets: TextInputEditText, MaterialButton, TextView, Snackbar y Toast.
 */
class DemoUiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.ui_demo_title)

        binding.buttonShow.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()

            if (name.isEmpty()) {
                binding.textInputName.error = getString(R.string.required_field)
                return@setOnClickListener
            }

            binding.textInputName.error = null
            binding.textViewGreeting.text = getString(R.string.greeting, name)

            binding.buttonShow.visibility = View.GONE
            binding.buttonReset.visibility = View.VISIBLE

            // Snackbar (barra temporal con acción opcional)
            Snackbar.make(
                binding.root,
                getString(R.string.snackbar_greeting, name),
                Snackbar.LENGTH_LONG
            ).setAction(R.string.undo) {
                // Acción opcional del Snackbar
            }.show()

            // Toast (mensaje flotante, no requiere interacción)
            Toast.makeText(this, getString(R.string.toast_greeting, name), Toast.LENGTH_SHORT).show()
        }

        binding.buttonReset.setOnClickListener {
            binding.editTextName.text?.clear()
            binding.textViewGreeting.text = getString(R.string.greeting_default)
            binding.buttonShow.visibility = View.VISIBLE
            binding.buttonReset.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}