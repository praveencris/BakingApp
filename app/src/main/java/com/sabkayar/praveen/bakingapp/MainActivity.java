package com.sabkayar.praveen.bakingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.bakingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        RecyclerView recyclerView = mBinding.rvRecipeMain;
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecipeAdapter = new RecipeAdapter(Utils.JSONUtils.getRecipes(this), this,this);
        recyclerView.setAdapter(mRecipeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();

    }

    @Override
    public void onItemClick(Utils.Recipe recipe) {
        Toast.makeText(this,"Clicked :"+recipe.getName(),Toast.LENGTH_SHORT).show();
    }
}
