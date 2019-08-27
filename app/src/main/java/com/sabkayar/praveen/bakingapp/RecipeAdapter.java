package com.sabkayar.praveen.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.bakingapp.databinding.RecipeMainItemLayoutBinding;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Utils.Recipe> mRecipeList;

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Utils.Recipe recipe);
    }

    public RecipeAdapter(List<Utils.Recipe> recipeList, OnItemClickListener listener, Context context) {
        mRecipeList = recipeList;
        mOnItemClickListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecipeMainItemLayoutBinding binding = RecipeMainItemLayoutBinding.inflate(inflater, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Utils.Recipe recipe = mRecipeList.get(position);
        holder.bind(recipe, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final RecipeMainItemLayoutBinding mBinding;

        public RecipeViewHolder(@NonNull RecipeMainItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Utils.Recipe recipe, final OnItemClickListener listener) {
            mBinding.tvRecipeName.setText(recipe.getName());
            mBinding.tvIngredients.setText(mContext.getString(R.string.n_ingredients, recipe.getIngredients().size()));
            mBinding.tvServings.setText(mContext.getString(R.string.n_servings, recipe.getServings()));
            mBinding.layoutRecipeMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(recipe);
                }
            });

        }
    }
}
