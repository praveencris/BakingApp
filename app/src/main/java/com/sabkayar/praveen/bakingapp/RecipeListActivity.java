package com.sabkayar.praveen.bakingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.bakingapp.databinding.ActivityRecipeListBinding;
import com.sabkayar.praveen.bakingapp.model.Recipe;
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


        RecyclerView recyclerView = mBinding.rvRecipeMain;
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecipeAdapter = new RecipeAdapter(Utils.JSONUtils.getRecipes(this), this, this);
        recyclerView.setAdapter(mRecipeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();

    }

    @Override
    public void onItemClick(Recipe recipe) {
        startActivity(RecipeDetailActivity.newIntent(this,recipe));
    }
}
