package com.example.android.gymondoautomationtest;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class LoginTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void emailIsEmpty() {
        LoginPageObject.cleanEmail();
        LoginPageObject.clickLogin();
        // verify the toast message
        onView(withText("Username and/or password incorrect"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void passwordIsEmpty() {
        LoginPageObject.enterEmail("automation@gymondo.de");
        LoginPageObject.cleanPassword();
        LoginPageObject.clickLogin();
        // verify the toast message
        onView(withText("Username and/or password incorrect"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void emailIsIncorrect() {
        LoginPageObject.login("incorrect@email.com", "automation");
        // verify the toast message
        onView(withText("Username and/or password incorrect"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void passwordIsIncorrect() {
        LoginPageObject.login("automation@gymondo.de", "incorrect");
        // verify the toast message
        onView(withText("Username and/or password incorrect"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void loginSuccessfully_shouldShowSearchScreen() throws InterruptedException {
        LoginPageObject.login("automation@gymondo.de", "automation");
        SearchPageObject.verifySearchScreen();
    }

}
