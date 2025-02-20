package com.emil.videovoyage.presentation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.shimmer.hide()
        val videoAdapter = VideoAdapter(context = this)
        binding.videoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.videoRecyclerView.adapter = videoAdapter
        binding.swipeRefreshLayout.setOnRefreshListener {
           getVideo()
        }
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
        binding.shimmer.run {
            show()
            setShimmer(VideoVoyage.CUSTOM_SHIMMER)
            startShimmer()
        }
        videoViewModel.getVideoList(onSuccess = {
            binding.shimmer.stopShimmer()
            binding.shimmer.hide()
            binding.swipeRefreshLayout.isRefreshing = false }, onError = {
            binding.shimmer.stopShimmer()
            binding.shimmer.hide()
            binding.swipeRefreshLayout.isRefreshing = false
            if (!isInternetAvailable())
                showErrorMessage(R.string.no_internet_connection)
             else showErrorMessage(R.string.error_loading_data)
        })






    }
    private fun showErrorDialog(stringRes:Int) {
        val dialog = ErrorDialogFragment.newInstance(
            getString(stringRes)) {
            getVideo()
        }
        dialog.show(supportFragmentManager, "ErrorDialog")
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


}

