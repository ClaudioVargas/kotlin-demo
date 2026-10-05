package com.example.androiddemo.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddemo.R
import com.example.androiddemo.adapter.PostAdapter
import com.example.androiddemo.databinding.ActivityNetworkBinding
import com.example.androiddemo.viewmodel.PostViewModel
import com.example.androiddemo.viewmodel.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Demo 5 — Red (Retrofit + corrutinas + StateFlow)
 * Consume https://jsonplaceholder.typicode.com/posts y muestra el resultado.
 */
class DemoNetworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetworkBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.network_title)

        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        binding.buttonRetry.setOnClickListener { viewModel.loadPosts() }

        // Observar el StateFlow y reaccionar al estado de la petición
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerViewPosts.visibility = View.GONE
                        binding.textViewError.visibility = View.GONE
                        binding.buttonRetry.visibility = View.GONE
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerViewPosts.visibility = View.VISIBLE
                        binding.recyclerViewPosts.adapter = PostAdapter(state.posts)
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerViewPosts.visibility = View.GONE
                        binding.textViewError.visibility = View.VISIBLE
                        binding.textViewError.text = getString(R.string.error_fetching, state.message)
                        binding.buttonRetry.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}