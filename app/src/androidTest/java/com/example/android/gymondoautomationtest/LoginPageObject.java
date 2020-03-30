package com.example.android.gymondoautomationtest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginPageObject {

    public static void enterEmail(String email ){
        onView(withId(R.id.editText))
                .perform(typeText(email), closeSoftKeyboard());
    }

    public static void enterPassword(String password ){
        onView(withId(R.id.editText2))
                .perform(typeText(password), closeSoftKeyboard());
    }

    public static void clickLogin(){
        onView(withId(R.id.button)).perform(click());
    }

    public static void login(String email, String password){
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public static void cleanEmail(){
        onView(withId(R.id.editText)).perform(clearText());
    }

    public static void cleanPassword(){
        onView(withId(R.id.editText2)).perform(clearText());
    }

}
