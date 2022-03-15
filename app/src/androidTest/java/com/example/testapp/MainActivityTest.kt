package com.example.testapp

import android.Manifest
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.example.core_tests.BaseInstrumentedTest
import com.example.sdk.presentation.error.ErrorFragment
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

/**
 * This is a very basic test, as it tests only the negative scenario.
 * To test a positive scenario we'd need to somehow mock the camera image
 */
@LargeTest
class MainActivityTest : BaseInstrumentedTest() {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @get:Rule
    val permissionsRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Test
    fun idRecognitionFailure() {
        onView(withId(R.id.readTextBtn)).perform(click())

        onView(withId(R.id.takePhotoBtn)).perform(click())

        activityScenarioRule
            .scenario
            .waitForFragment<MainActivity, ErrorFragment>(ErrorFragment.TAG) {
                // If we use «onView» here, it just hangs
                // Probably it happens because this code is being executed on the activity's main thread
                assertEquals("No text found", view?.findViewById<TextView>(R.id.messageTv)?.text)

                // Ideally we should check a retry button here, but see the comment above
            }
    }

    @Test
    fun faceRecognitionFailure() {
        onView(withId(R.id.detectFaceBtn)).perform(click())

        onView(withId(R.id.takePhotoBtn)).perform(click())

        activityScenarioRule
            .scenario
            .waitForFragment<MainActivity, ErrorFragment>(ErrorFragment.TAG) {
                // If we use «onView» here, it just hangs
                // Probably it happens because this code is being executed on the activity's main thread
                assertEquals("No face found", view?.findViewById<TextView>(R.id.messageTv)?.text)

                // Ideally we should check a retry button here, but see the comment above
            }
    }

    /**
     * This is a terrible hack, I use it because default «waitFor» didn't work for me, and I don't have time to fix it
     */
    private inline fun <A : FragmentActivity, reified F : Fragment> ActivityScenario<A>.waitForFragment(
        fragmentTag: String,
        delay: Long = WAIT_FOR_FRAGMENT_DELAY,
        crossinline action: F.() -> Unit
    ) {
        sleep(delay)

        onActivity { activity ->
            val fragment = activity.supportFragmentManager.findFragmentByTag(fragmentTag)

            assertNotNull(fragment)
            assertIs<F>(fragment)

            action(fragment)
        }
    }

    private companion object {
        private const val WAIT_FOR_FRAGMENT_DELAY = 5_000L
    }
}
