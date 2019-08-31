package com.sabkayar.praveen.bakingapp;

import android.widget.TextView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.sabkayar.praveen.bakingapp.ui.activities.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeListActivityScreenTest {
    public static final String RECIPE_NAME = "Brownies";
    private static final String STEP_SHORT_DESCRIPTION = "0" + " : " +"Recipe Introduction";
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipeListActivity.class);


    /**
     * Clicks on a RecycleView item and checks it opens up the RecipeDetailActivity with the correct details.
     */
    @Test
    public void clickRecycleViewItem_OpensRecipeDetailActivity() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // recyclerView item and clicks it.
        onView(withId(R.id.rv_recipe_main)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Checks that the RecipeDetailActivity opens with the correct tea name displayed
        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar"))))
                .check(matches(withText(RECIPE_NAME)));

        clickRecycleViewItem_OpensRecipeStepDetailActivity();

    }


    /**
     * Clicks on a RecycleView item and checks it opens up the RecipeStepDetailActivity with the correct details.
     */
    public void clickRecycleViewItem_OpensRecipeStepDetailActivity() {
        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // recyclerView item and clicks it.
        onView(withId(R.id.rv_recipe_detail)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the RecipeDetailActivity opens with the correct tea name displayed
        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar"))))
                .check(matches(withText(STEP_SHORT_DESCRIPTION)));


    }

}
