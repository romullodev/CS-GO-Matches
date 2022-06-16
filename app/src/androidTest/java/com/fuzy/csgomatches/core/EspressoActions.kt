package com.fuzy.csgomatches.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

class EspressoActions {
    companion object {

        fun checkTextOnAlertDialog(resourceText: Int) {
            Espresso.onView(ViewMatchers.withText(resourceText))
                .inRoot(RootMatchers.isDialog())
                .check(
                    ViewAssertions.matches(
                        ViewMatchers.isDisplayed()
                    )
                )
        }

        fun performClickOnViewInsideRecyclerView(
            recyclerId: Int,
            pos: Int,
            viewId: Int
        ) {
            Espresso.onView(ViewMatchers.withId(recyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                    (pos, CustomClickOnView(viewId))
            )
        }

        fun checkSizeOnRecyclerView(rv: Int, size: Int) {
            Espresso.onView(ViewMatchers.withId(rv))
                .check(ViewAssertions.matches(hasItemCountOnRecyclerView(size)))
        }

        @JvmStatic
        private fun hasItemCountOnRecyclerView(itemCount: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(
                RecyclerView::class.java
            ) {

                override fun describeTo(description: Description) {
                    description.appendText("has $itemCount items")
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    return view.adapter?.itemCount == itemCount
                }
            }
        }

    }
}
private class CustomClickOnView(val viewId: Int) : ViewAction {
    val click: ViewAction = ViewActions.click()
    override fun getConstraints(): Matcher<View> = click.constraints
    override fun getDescription(): String = " click on view"
    override fun perform(uiController: UiController, view: View): Unit =
        click.perform(uiController, view.findViewById(viewId))
}
