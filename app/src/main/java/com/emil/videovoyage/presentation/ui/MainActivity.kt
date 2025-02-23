package com.emil.videovoyage.presentation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.emil.videovoyage.R
import com.emil.videovoyage.adapter.VideoAdapter
import com.emil.videovoyage.databinding.ActivityMainBinding
import com.emil.videovoyage.presentation.viewmodel.VideoViewModel
import com.emil.videovoyage.util.VideoVoyage
import com.emil.videovoyage.util.hide
import com.emil.videovoyage.util.isInternetAvailable
import com.emil.videovoyage.util.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val videoViewModel by viewModel<VideoViewModel> ()
    private val videoAdapter:VideoAdapter by lazy{
        VideoAdapter(context = this)
    }
    companion object {
        private const val SPAN_COUNT = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.shimmer.hide()
        setupRecyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isInternetAvailable()) {
                getVideo()
            } else showErrorMessage(R.string.no_internet_connection)
        }
        observeVideoList()
    }
    private fun setupRecyclerView() {
        val layoutManager = if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this, SPAN_COUNT)
        } else {
            LinearLayoutManager(this)
        }

        binding.videoRecyclerView.layoutManager = layoutManager
        binding.videoRecyclerView.adapter = videoAdapter
    }
    private fun observeVideoList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                videoViewModel.videoList.collect { video ->
                    binding.videoRecyclerView.apply {
                        videoAdapter.submitVideo(
                            video ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun getVideo () {
        showLoading(true)
        videoViewModel.getVideoList(onSuccess = { showLoading(false) }, onError = {
            showLoading(false)
            showErrorMessage(if (!isInternetAvailable()) R.string.no_internet_connection else R.string.error_loading_data)
        })


    }

    private fun showLoading(show: Boolean) {
        binding.shimmer.apply {
            if (show) {
                show()
                setShimmer(VideoVoyage.CUSTOM_SHIMMER)
                startShimmer()
            } else {
                stopShimmer()
                hide()
            }
        }
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showErrorMessage(stringRes: Int) {
        binding.root.let { rootView ->
            Snackbar.make(rootView, getString(stringRes), Snackbar.LENGTH_INDEFINITE).apply {
                setBackgroundTint(Color.WHITE)
                setTextColor(Color.GRAY)
                setAction(getString(R.string.repeat)) {
                    getVideo()
                }
                setActionTextColor(Color.BLUE)
                show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (videoViewModel.videoList.value.isNullOrEmpty()) {
            getVideo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoRecyclerView.adapter = null
    }
}

