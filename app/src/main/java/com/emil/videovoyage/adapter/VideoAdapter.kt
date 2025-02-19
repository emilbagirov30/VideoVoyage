package com.emil.videovoyage.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emil.domain.model.Video
import com.emil.videovoyage.databinding.RvItemVideoBinding
import com.emil.videovoyage.util.toVideoDuration

class VideoAdapter (private val context: Context): ListAdapter<Video, VideoAdapter.VideoViewHolder>(HotelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = RvItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder:VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VideoViewHolder(private val binding: RvItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            binding.apply {
                videoNameTextView.text = video.name
                videoDurationTextView.text = video.duration.toVideoDuration()
                Glide.with(context)
                    .load(Uri.parse(video.url))
                    .frame(0)
                    .into(frameImageView)
            }


            binding.root.setOnClickListener {

            }
        }
    }

    class HotelDiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean = oldItem == newItem
    }

    fun submitVideo(videoList: List<Video>) {
        submitList(videoList)
    }
}
