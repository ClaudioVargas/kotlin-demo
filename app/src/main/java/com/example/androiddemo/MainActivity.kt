package com.example.androiddemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddemo.adapter.DemoAdapter
import com.example.androiddemo.databinding.ActivityMainBinding
import com.example.androiddemo.model.DemoItem
import com.example.androiddemo.ui.DemoCameraActivity
import com.example.androiddemo.ui.DemoCoroutinesActivity
import com.example.androiddemo.ui.DemoCounterActivity
import com.example.androiddemo.ui.DemoNetworkActivity
import com.example.androiddemo.ui.DemoNotificationActivity
import com.example.androiddemo.ui.DemoPermissionsActivity
import com.example.androiddemo.ui.DemoPrefsActivity
import com.example.androiddemo.ui.DemoSensorActivity
import com.example.androiddemo.ui.DemoUiActivity

/**
 * Pantalla principal: muestra un RecyclerView con todos los demos disponibles.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.app_name)
        supportActionBar?.setSubtitle(R.string.app_description)

        val demos = listOf(
            DemoItem(1, R.string.ui_demo_title, R.string.ui_demo_description, android.R.drawable.ic_menu_edit),
            DemoItem(2, R.string.counter_title, R.string.counter_description, android.R.drawable.ic_menu_manage),
            DemoItem(3, R.string.coroutines_title, R.string.coroutines_description, android.R.drawable.ic_popup_sync),
            DemoItem(4, R.string.prefs_title, R.string.prefs_description, android.R.drawable.ic_menu_save),
            DemoItem(5, R.string.network_title, R.string.network_description, android.R.drawable.ic_menu_upload),
            DemoItem(6, R.string.camera_title, R.string.camera_description, android.R.drawable.ic_menu_camera),
            DemoItem(7, R.string.sensor_title, R.string.sensor_description, android.R.drawable.ic_menu_compass),
            DemoItem(8, R.string.notification_title, R.string.notification_description, android.R.drawable.ic_dialog_info),
            DemoItem(9, R.string.permissions_title, R.string.permissions_description, android.R.drawable.ic_lock_lock)
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = DemoAdapter(demos) { item ->
            val intent = when (item.id) {
                1 -> Intent(this, DemoUiActivity::class.java)
                2 -> Intent(this, DemoCounterActivity::class.java)
                3 -> Intent(this, DemoCoroutinesActivity::class.java)
                4 -> Intent(this, DemoPrefsActivity::class.java)
                5 -> Intent(this, DemoNetworkActivity::class.java)
                6 -> Intent(this, DemoCameraActivity::class.java)
                7 -> Intent(this, DemoSensorActivity::class.java)
                8 -> Intent(this, DemoNotificationActivity::class.java)
                9 -> Intent(this, DemoPermissionsActivity::class.java)
                else -> return@DemoAdapter
            }
            startActivity(intent)
        }
    }
}