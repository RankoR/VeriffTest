package com.example.sdk.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.sdk.R
import com.example.sdk.presentation.loading.LoadingFragment
import org.junit.Test

class LoadingFragmentTest {

    @Test
    fun loaderShown() {
        launchFragmentInContainer<LoadingFragment>()

        onView(withId(R.id.progressBar)).check(matches(isCompletelyDisplayed()))
    }
}
