package com.emil.videovoyage.presentation.ui

import android.content.pm.ActivityInfo
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.emil.videovoyage.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class VideoPlayerDialogFragmentTest {
    private val videoUrl = "test_url"
    private val videoName = "Test Video"
    @JvmField
    @Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private fun launchVideoPlayerDialog(url: String, name: String) {
        val dialogFragment = VideoPlayerDialogFragment.newInstance(url, name)

        activityRule.scenario.onActivity { activity ->
            dialogFragment.show(activity.supportFragmentManager, "VideoPlayerDialogFragment")
        }

    }

    @Test
    fun testVideoPlayerDialog_DisplaysCorrectVideoAndName() {

        launchVideoPlayerDialog(videoUrl, videoName)

        onView(withId(R.id.videoNameTextView)).check(matches(withText(videoName)))

        onView(withId(R.id.playerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testVideoPlayerDialog_ClosesOnBackPressed() {

        launchVideoPlayerDialog(videoUrl, videoName)

        pressBack()

        onView(withId(R.id.playerView)).check(doesNotExist())
    }


    @Test
    fun testVideoPlayerDialog_RetainsPlaybackStateOnConfigurationChange() {
        launchVideoPlayerDialog(videoUrl, videoName)

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onView(withId(R.id.playerView)).check(matches(isDisplayed()))
    }

}
