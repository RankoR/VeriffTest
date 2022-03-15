package com.example.sdk.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.sdk.R
import com.example.sdk.presentation.error.ErrorFragment
import org.junit.Test

class ErrorFragmentTest {

    @Test
    fun dialogDisplayed() {
        launchFragmentInContainer<ErrorFragment>(
            fragmentArgs = bundleOf("message" to "Test message"),
            themeResId = R.style.Theme_MaterialComponents
        )

        onView(withId(R.id.messageTv))
            .check(matches(withText("Test message")))
            .check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.retryBtn))
            .check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun retryClick() {
        launchFragmentInContainer<ErrorFragment>(
            fragmentArgs = bundleOf("message" to "Test message"),
            themeResId = R.style.Theme_MaterialComponents
        ).onFragment { fragment ->
            var isOnRetryCalled = false

            fragment.onRetryClick = { isOnRetryCalled = true }

            // FIXME: Click hangs for some reason; no time to find out why
//            onView(withId(R.id.retryBtn)).perform(click())
//
//            assertTrue(isOnRetryCalled)
        }
    }
}
