package com.example.oechapp

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.oechapp.ui.fragment.onboard.OnBoardFirstItemFragment
import com.example.oechapp.ui.fragment.onboard.OnBoardSecondItemFragment
import com.example.oechapp.ui.fragment.onboard.OnBoardThirdItemFragment
import com.example.oechapp.ui.fragment.onboard.utils.OnBoardStore
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class OnBoardInstrumentedTest {
    @Test
    fun testFirstFragment() {
        val activeItem = OnBoardStore.items[0]

        val navController = createFragment(OnBoardFirstItemFragment(), R.id.onboard_first)

        onView(withId(R.id.title)).check(matches(withText(activeItem.title)))
        onView(withId(R.id.description)).check(matches(withText(activeItem.description)))
        onView(withId(R.id.image)).check(matches(withTagValue(equalTo(activeItem.image))))

        onView(withId(R.id.skip)).check(matches(isDisplayed()))
        onView(withId(R.id.next)).check(matches(isDisplayed()))

        onView(withId(R.id.next)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.onboard_second)
    }

    @Test
    fun testSecondFragment() {
        val activeItem = OnBoardStore.items[1]

        val navController = createFragment(OnBoardSecondItemFragment(), R.id.onboard_second)

        onView(withId(R.id.title)).check(matches(withText(activeItem.title)))
        onView(withId(R.id.description)).check(matches(withText(activeItem.description)))
        onView(withId(R.id.image)).check(matches(withTagValue(equalTo(activeItem.image))))

        onView(withId(R.id.skip)).check(matches(isDisplayed()))
        onView(withId(R.id.next)).check(matches(isDisplayed()))

        onView(withId(R.id.next)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.onboard_third)
    }

    @Test
    fun testThirdFragment() {
        val activeItem = OnBoardStore.items[2]

        val navController = createFragment(OnBoardThirdItemFragment(), R.id.onboard_third)

        onView(withId(R.id.title)).check(matches(withText(activeItem.title)))
        onView(withId(R.id.description)).check(matches(withText(activeItem.description)))
        onView(withId(R.id.image)).check(matches(withTagValue(equalTo(activeItem.image))))

        onView(withId(R.id.signup)).check(matches(isDisplayed()))
        onView(withId(R.id.signin)).check(matches(isDisplayed()))

        assertThrows(NoMatchingViewException::class.java) {
            onView(withId(R.id.next)).check(matches(isDisplayed()))
            onView(withId(R.id.skip)).check(matches(isDisplayed()))
        }
    }

    private inline fun <reified T : Fragment> createFragment(
        fragment: T,
        currDestination: Int
    ): TestNavHostController {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val fragmentScenario = launchFragmentInContainer(
            fragmentArgs = bundleOf(),
            themeResId = R.style.Theme_OechApp,
            instantiate = { fragment }
        )

        fragmentScenario.withFragment {
            navController.setGraph(R.navigation.onboard_nav_graph)
            navController.setCurrentDestination(currDestination)

            fragmentScenario.moveToState(Lifecycle.State.STARTED)

            Navigation.setViewNavController(requireView(), navController)
        }

        return navController
    }
}