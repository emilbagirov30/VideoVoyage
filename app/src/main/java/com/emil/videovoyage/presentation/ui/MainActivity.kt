package com.emil.videovoyage.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.emil.videovoyage.adapter.VideoAdapter
import com.emil.videovoyage.databinding.ActivityMainBinding
import com.emil.videovoyage.presentation.viewmodel.VideoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val videoViewModel by viewModel<VideoViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val videoAdapter = VideoAdapter(context = this)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                videoViewModel.videoList.collect { video ->
                    binding.videoRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = videoAdapter
                        videoAdapter.submitVideo(
                            video ?: emptyList()
                        )
                    }
                }
            }
        }
                   getVideo()

    }
    private fun getVideo () {
        videoViewModel.getVideoList(onSuccess = {}, onError = {})
    }
}