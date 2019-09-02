package com.sabkayar.praveen.bakingapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.sabkayar.praveen.bakingapp.R;
import com.sabkayar.praveen.bakingapp.databinding.ActivityFragmentContainerBinding;
import com.sabkayar.praveen.bakingapp.model.Recipe;
import com.sabkayar.praveen.bakingapp.model.Step;
import com.sabkayar.praveen.bakingapp.ui.fragments.RecipeDetailFragment;
import com.sabkayar.praveen.bakingapp.ui.fragments.RecipeStepDetailFragment;
import com.sabkayar.praveen.bakingapp.utils.Utils;

import java.util.List;
import java.util.Objects;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener, RecipeStepDetailFragment.OnFragmentInteractionListener {
    public static final String RECIPE_EXTRA = "recipe_extra";
    private ActivityFragmentContainerBinding mBinding;
    private boolean isTwoPane;

    public static Intent newIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_fragment_container);
        isTwoPane = mBinding.fragmentStepDetailContainer != null;


        Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRA);
        if (recipe != null) {
            setTitle(recipe.getName());
            Utils.saveLastSeenRecipeId(this, recipe.getId());
        }
        if (savedInstanceState == null) {

            if (isTwoPane) {
                //Attach fragment
                RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().
                        add(R.id.fragment_container, recipeDetailFragment)
                        .commit();

                // Add Fragment
                RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(Objects.requireNonNull(recipe).getSteps(), 0);
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_step_detail_container, recipeStepDetailFragment)
                        .commit();
            } else {
                //Attach fragment
                RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().
                        add(R.id.fragment_container, recipeDetailFragment)
                        .commit();
            }

        }
    }

    @Override
    public void onFragmentInteraction(List<Step> steps, int position) {
        //Toast.makeText(this, "Clicked :" + step.getDescription(), Toast.LENGTH_SHORT).show();
        if (isTwoPane) {
            // Replace Fragment
            RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(steps, position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_step_detail_container, recipeStepDetailFragment)
                    .commit();
        } else {
            Intent intent = RecipeStepDetailActivity.newIntent(this, steps, position);
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
