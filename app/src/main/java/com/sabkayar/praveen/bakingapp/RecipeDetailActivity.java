package com.sabkayar.praveen.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.sabkayar.praveen.bakingapp.databinding.ActivityRecipeDetailBinding;
import com.sabkayar.praveen.bakingapp.model.Recipe;
import com.sabkayar.praveen.bakingapp.model.Step;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener {
    private static final String RECIPE_EXTRA = "recipe_extra";

    public static Intent newIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRA);
        if (recipe != null) {
            setTitle(recipe.getName());
            //Attach fragment
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    add(R.id.fragment_detail_container, recipeDetailFragment)
                    .commit();
        }

    }

    @Override
    public void onFragmentInteraction(Step step) {
        Toast.makeText(this, "Clicked :" + step.getDescription(), Toast.LENGTH_SHORT).show();
        Intent intent = RecipeStepDetailActivity.newIntent(this, step);
        startActivity(intent);
    }
}
