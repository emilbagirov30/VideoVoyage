package com.emil.videovoyage.presentation.ui



import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.emil.videovoyage.R
import com.emil.videovoyage.adapter.VideoAdapter

import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @JvmField
    @Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewIsDisplayed() {
        onView(withId(R.id.videoRecyclerView)).check(matches(isDisplayed()))
    }


    @Test
    fun testVideosAreLoaded() {
        onView(withId(R.id.videoRecyclerView)).check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testSwipeToRefresh() {
        onView(withId(R.id.swipeRefreshLayout)).perform(swipeDown())
    }


    @Test
    fun testScrollRecyclerView() {
        onView(withId(R.id.videoRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<VideoAdapter.VideoViewHolder>(4))
    }


}