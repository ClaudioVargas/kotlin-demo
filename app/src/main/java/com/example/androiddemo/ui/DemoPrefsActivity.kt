package com.example.androiddemo.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.R
import com.example.androiddemo.databinding.ActivityPrefsBinding

/**
 * Demo 4 — SharedPreferences
 * Guarda/lee un texto en almacenamiento local de tipo clave-valor.
 */
class DemoPrefsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrefsBinding

    private val prefs by lazy {
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrefsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.prefs_title)

        binding.textViewStored.text = getString(R.string.no_value)

        binding.buttonSave.setOnClickListener {
            val text = binding.editTextValue.text.toString().trim()
            // apply() escribe en memoria y guarda en disco de forma asíncrona
            prefs.edit().putString(KEY_VALUE, text).apply()
            binding.textViewStored.text = getString(R.string.saved_value, text)
        }

        binding.buttonLoad.setOnClickListener {
            val saved = prefs.getString(KEY_VALUE, null)
            binding.textViewStored.text = if (saved != null) {
                getString(R.string.loaded_value, saved)
            } else {
                getString(R.string.no_value)
            }
        }

        binding.buttonDelete.setOnClickListener {
            prefs.edit().remove(KEY_VALUE).commit() // commit() es síncrono
            binding.editTextValue.text?.clear()
            binding.textViewStored.text = getString(R.string.no_value)
        }
    }

    companion object {
        private const val PREFS_NAME = "demo_prefs"
        private const val KEY_VALUE = "saved_value"
    }
}