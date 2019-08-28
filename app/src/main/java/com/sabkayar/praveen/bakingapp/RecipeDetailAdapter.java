package com.sabkayar.praveen.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.bakingapp.databinding.RecipeDetailItemLayoutBinding;
import com.sabkayar.praveen.bakingapp.model.Step;

import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder> {

    private List<Step> mStepList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public RecipeDetailAdapter(List<Step> stepList, Context context, OnItemClickListener listener) {
        mStepList = stepList;
        mContext = context;
        mOnItemClickListener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(Step step);

    }

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecipeDetailItemLayoutBinding binding =
                RecipeDetailItemLayoutBinding.inflate(inflater, parent, false);
        return new RecipeDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder holder, int position) {
        Step step = mStepList.get(position);
        holder.bind(step, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (mStepList == null) {
            return 0;
        }
        return mStepList.size();
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder {
        private RecipeDetailItemLayoutBinding mBinding;

        public RecipeDetailViewHolder(@NonNull RecipeDetailItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Step step, final OnItemClickListener listener) {
            mBinding.tvStepCount.setText(mContext.getString(R.string.step_n, step.getId()+1));
            mBinding.tvShortDescription.setText(step.getShortDescription());
            mBinding.layoutRecipeDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(step);
                }
            });
        }
    }
}
