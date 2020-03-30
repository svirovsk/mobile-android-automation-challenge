package com.example.android.gymondoautomationtest;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class SearchPageObject {


    public static void verifySearchScreen(){
        onView(withId(R.id.editTxtSearch)).check(matches(isDisplayed()));
    }

    public static void enterExercise(String exercise){
        onView(withId(R.id.editTxtSearch))
                .perform(typeText(exercise), closeSoftKeyboard());
    }
    public static void clickSearch(){
        onView(withId(R.id.btnSearch)).perform(click());
    }

    public static void clickClear(){
        onView(withId(R.id.btnClear)).perform(click());
    }

    public static void areSearchedItemsShown(String text){
        onView(withId(R.id.recycler_view)).check(matches(contains(withSubstring(text))));
    }

    public static void isSearchTextFieldEmpty(){
        onView(withId(R.id.editTxtSearch)).check(matches(withText("")));
    }

    public static void noItemsFound(){
        onView(withId(R.id.recycler_view)).check(isEmpty());
    }

    public static void listNotEmpty(){
        onView(withId(R.id.recycler_view)).check((notEmpty()));
    }

    /**
     * Asserts that RecyclerView has more than one item and is not empty
     **/
    private static ViewAssertion notEmpty() {
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ViewMatchers.assertThat(rv.getAdapter().getItemCount(), greaterThan(0));
            }
        };
    }

    /**
     * Asserts that the RecyclerView is Empty
     */
    private static ViewAssertion isEmpty() {
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ViewMatchers.assertThat(rv.getAdapter().getItemCount(), is(0));
            }
        };
    }

    /**
     * Returns a matcher that matches all RecyclerView items based on the given itemMatcher.
     * @param itemMatcher
     */
    public static Matcher<View> contains( @NonNull final Matcher<View> itemMatcher) {
        Preconditions.checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("items ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder;
                for (int i = 0; i < view.getChildCount(); i++) {
                    viewHolder = view.findViewHolderForAdapterPosition(i);
                    if (itemMatcher.matches(viewHolder.itemView)==false){
                        //item doesn't match
                        return false;
                    }
                }
                return true;
            }
        };
    }
}
