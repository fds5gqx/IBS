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
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Test
    public void mainActivityTest2() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.id_input),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("aaaa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.pwd_input),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("bbbbb"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login_button), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.login_button2), withText("Sign up"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.id_input), withText("aaaa"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.id_input), withText("aaaa"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("a"));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.id_input), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.pwd_input), withText("bbbbb"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("b"));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.pwd_input), withText("b"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.login_button), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.safety), withText("자전거 탑승 시 안전수칙"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatButton5.perform(scrollTo(), click());

        pressBack();

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.login_button), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.layout_2),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.safety), withText("자전거 탑승 시 안전수칙"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.btn_expand_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.btn_expand_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.btn_expand_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.btn_expand_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        2),
                                1),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.btn_expand_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        4),
                                1),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(R.id.btn_expand_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        6),
                                1),
                        isDisplayed()));
        appCompatImageView6.perform(click());

        ViewInteraction appCompatImageView7 = onView(
                allOf(withId(R.id.btn_expand_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        7),
                                1),
                        isDisplayed()));
        appCompatImageView7.perform(click());

        pressBack();

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.Fianece), withText("LED 제어"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.colorpick2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button2), withText("cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.colorpick2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(android.R.id.button1), withText("ok"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton10.perform(scrollTo(), click());

        pressBack();

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.Work), withText("현재 위치 확인"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatButton11.perform(scrollTo(), click());
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
