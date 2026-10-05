package com.example.androiddemo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.databinding.ActivityCounterBinding
import com.example.androiddemo.viewmodel.CounterViewModel

/**
 * Demo 2 — ViewModel + LiveData
 * Prueba: contador en el ViewModel; al rotar la pantalla el valor se mantiene.
 */
class DemoCounterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCounterBinding

    // viewModels() (de activity-ktx) crea/obtiene el ViewModel asociado a la Activity
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.counter_title)

        // Observar el LiveData: se ejecuta cada vez que cambia el valor
        viewModel.count.observe(this) { value ->
            binding.textViewCount.text = value.toString()
        }

        binding.buttonPlus.setOnClickListener { viewModel.increment() }
        binding.buttonMinus.setOnClickListener { viewModel.decrement() }
        binding.buttonReset.setOnClickListener { viewModel.reset() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}