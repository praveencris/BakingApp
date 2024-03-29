package com.sabkayar.praveen.bakingapp.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.bakingapp.R;
import com.sabkayar.praveen.bakingapp.databinding.RecipeMainItemLayoutBinding;
import com.sabkayar.praveen.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipeList, OnItemClickListener listener, Context context) {
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
        Recipe recipe = mRecipeList.get(position);
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

        public void bind(final Recipe recipe, final OnItemClickListener listener) {
            mBinding.tvRecipeName.setText(recipe.getName());
            mBinding.tvIngredients.setText(mContext.getString(R.string.n_ingredients, recipe.getIngredients().size()));
            mBinding.tvServings.setText(mContext.getString(R.string.n_servings, recipe.getServings()));
            if (!TextUtils.isEmpty(recipe.getImage()))
                Picasso.get().load(recipe.getImage()).placeholder(R.drawable.progress_animation)
                        .error(R.drawable.error_placeholder).into(mBinding.imvRecipe);

            mBinding.layoutRecipeMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(recipe);
                }
            });

        }
    }
}
