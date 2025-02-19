package com.emil.videovoyage.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View


fun Int.toVideoDuration(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return if (hours > 0) {
        "%d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%d:%02d".format(minutes, seconds)
    }
}


fun View.hide () {
    this.visibility = View.GONE
}

fun View.show () {
    this.visibility = View.VISIBLE
}

fun View.anim() {
    fun createScaleAnimator(scaleXStart: Float, scaleXEnd: Float, scaleYStart: Float, scaleYEnd: Float): AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, scaleXStart, scaleXEnd)
        val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleYStart, scaleYEnd)
        scaleX.duration = 200
        scaleY.duration = 200
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleX).with(scaleY)
        return animatorSet
    }

    val scaleDown = createScaleAnimator(1f, 0.5f, 1f, 0.5f)
    val scaleUp = createScaleAnimator(0.5f, 1f, 0.5f, 1f)

    val animatorSet = AnimatorSet()
    animatorSet.play(scaleDown).before(scaleUp)
    animatorSet.start()
}