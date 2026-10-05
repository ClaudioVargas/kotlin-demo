package com.example.androiddemo.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.R
import com.example.androiddemo.databinding.ActivitySensorBinding

/**
 * Demo 7 — Sensor Acelerómetro
 * Lee los valores X, Y, Z del acelerómetro en tiempo real.
 * Prueba en un dispositivo físico: los emuladores suelen simularlo.
 */
class DemoSensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivitySensorBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.sensor_title)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            binding.textViewSensorStatus.text = getString(R.string.sensor_not_available)
            binding.textViewSensorStatus.setTextColor(getColor(android.R.color.holo_red_dark))
        }
    }

    override fun onResume() {
        super.onResume()
        // Registrar el listener: recibiremos eventos en cada cambio de sensor
        accelerometer?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        // Importante: liberar el sensor para ahorrar batería
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        binding.textViewX.text = getString(R.string.sensor_value, "X", x)
        binding.textViewY.text = getString(R.string.sensor_value, "Y", y)
        binding.textViewZ.text = getString(R.string.sensor_value, "Z", z)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se usa en este ejemplo
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}