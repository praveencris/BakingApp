package com.sabkayar.praveen.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.sabkayar.praveen.bakingapp.model.Step;

public class RecipeStepDetailActivity extends AppCompatActivity implements RecipeStepDetailFragment.OnFragmentInteractionListener {

    private static final String STEP_EXTRA = "step_extra";

    public static Intent newIntent(Context context, Step step) {
        Intent intent = new Intent(context, RecipeStepDetailActivity.class);
        intent.putExtra(STEP_EXTRA, step);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Step step = getIntent().getParcelableExtra(STEP_EXTRA);
        if (step != null)
            setTitle(step.getShortDescription());
        // Add Fragment
        RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(step, null);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_detail_container, recipeStepDetailFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
