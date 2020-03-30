package com.example.android.gymondoautomationtest;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class SearchTest {

    @Rule
    public ActivityTestRule<ListActivity> mActivityTestRule = new ActivityTestRule<>(ListActivity.class);

    @Test
    public void clearSearchFieldBeforeSearch(){
        SearchPageObject.enterExercise("436 - 10 Min Abs");
        SearchPageObject.clickClear();
        SearchPageObject.isSearchTextFieldEmpty();
    }

    @Test
    public void itemNotFound() {
        SearchPageObject.enterExercise("123456");
        SearchPageObject.clickSearch();
        SearchPageObject.noItemsFound();
    }

    @Test
    public void clearSearchFieldAfterSearch() {
        //search for non existing item
        SearchPageObject.enterExercise("123456");
        SearchPageObject.clickSearch();
        //the list should be empty
        SearchPageObject.noItemsFound();
        SearchPageObject.clickClear();
        //after clear the list is full again
        SearchPageObject.listNotEmpty();
    }

    @Test
    public void searchSuccessful(){
        SearchPageObject.enterExercise("Abs");
        SearchPageObject.clickSearch();
        SearchPageObject.areSearchedItemsShown("Abs");
    }
}
