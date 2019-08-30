package com.sabkayar.praveen.bakingapp.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.bakingapp.R;
import com.sabkayar.praveen.bakingapp.databinding.ActivityRecipeListBinding;
import com.sabkayar.praveen.bakingapp.model.Recipe;
import com.sabkayar.praveen.bakingapp.ui.RecipeAdapter;
import com.sabkayar.praveen.bakingapp.utils.Utils;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    private static final String LOG_TAG = RecipeListActivity.class.getSimpleName();

    private ActivityRecipeListBinding mBinding;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list);

        if (!isTablet()) {
            RecyclerView recyclerView = mBinding.rvRecipeMain;
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            mRecipeAdapter = new RecipeAdapter(Utils.JSONUtils.getRecipes(this), this, this);
            recyclerView.setAdapter(mRecipeAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.hasFixedSize();
        } else {
            RecyclerView recyclerView = mBinding.rvRecipeMain;
            GridLayoutManager gridLayoutManager =
                    new GridLayoutManager(this, 2);
            mRecipeAdapter = new RecipeAdapter(Utils.JSONUtils.getRecipes(this), this, this);
            recyclerView.setAdapter(mRecipeAdapter);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

    }

    private boolean isTablet() {
        Configuration config = getResources().getConfiguration();
        return config.smallestScreenWidthDp >= 600;
    }

    @Override
    public void onItemClick(Recipe recipe) {
        startActivity(RecipeDetailActivity.newIntent(this, recipe));
    }
}
