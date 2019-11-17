package com.example.gmp.exdbproject;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LEDTest {

    @Rule
    public ActivityTestRule<LEDView> mActivityTestRule = new ActivityTestRule<>(LEDView.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Before
    public void loding() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int red = 40;
        int green = 40;
        int blue = 40;
    }

    @Test
    public void lEDTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.colorpick2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("ok"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction toggleButton = onView(
                allOf(withText("23"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        7),
                                2)));
        toggleButton.perform(scrollTo(), click());

        ViewInteraction toggleButton2 = onView(
                allOf(withText("22"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        6),
                                2)));
        toggleButton2.perform(scrollTo(), click());

        ViewInteraction toggleButton3 = onView(
                allOf(withText("21"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        5),
                                2)));
        toggleButton3.perform(scrollTo(), click());

        ViewInteraction toggleButton4 = onView(
                allOf(withText("20"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        4),
                                2)));
        toggleButton4.perform(scrollTo(), click());

        ViewInteraction toggleButton5 = onView(
                allOf(withText("19"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        3),
                                2)));
        toggleButton5.perform(scrollTo(), click());

        ViewInteraction toggleButton6 = onView(
                allOf(withText("18"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        2),
                                2)));
        toggleButton6.perform(scrollTo(), click());

        ViewInteraction toggleButton7 = onView(
                allOf(withText("17"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        1),
                                2)));
        toggleButton7.perform(scrollTo(), click());

        ViewInteraction toggleButton8 = onView(
                allOf(withText("16"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        0),
                                2)));
        toggleButton8.perform(scrollTo(), click());

        ViewInteraction toggleButton9 = onView(
                allOf(withText("19"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        3),
                                2)));
        toggleButton9.perform(scrollTo(), click());

        ViewInteraction toggleButton10 = onView(
                allOf(withText("21"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        5),
                                2)));
        toggleButton10.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.colorpick2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("ok"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction toggleButton11 = onView(
                allOf(withText("18"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        2),
                                2)));
        toggleButton11.perform(scrollTo(), click());

        ViewInteraction toggleButton12 = onView(
                allOf(withText("18"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        2),
                                2)));
        toggleButton12.perform(scrollTo(), click());

        ViewInteraction toggleButton13 = onView(
                allOf(withText("34"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        2),
                                4)));
        toggleButton13.perform(scrollTo(), click());

        ViewInteraction toggleButton14 = onView(
                allOf(withText("67"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.table),
                                        3),
                                8)));
        toggleButton14.perform(scrollTo(), click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.buttonB), withText("블루투스 연결"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatButton6.perform(click());

        pressBack();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
