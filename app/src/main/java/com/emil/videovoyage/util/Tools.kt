package com.emil.videovoyage.util

import android.graphics.Color
import com.facebook.shimmer.Shimmer

class VideoVoyage {
    companion object {
        val CUSTOM_SHIMMER: Shimmer? = Shimmer.ColorHighlightBuilder()
            .setBaseColor(Color.GRAY)
            .setHighlightColor(Color.WHITE)
            .setWidthRatio(1.5f)
            .setIntensity(0.01f)
            .setDuration(1200)
            .build()
    }

}
