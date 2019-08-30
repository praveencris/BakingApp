package com.sabkayar.praveen.bakingapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sabkayar.praveen.bakingapp.R;
import com.sabkayar.praveen.bakingapp.databinding.ActivityStepDetailsBinding;
import com.sabkayar.praveen.bakingapp.model.Step;
import com.sabkayar.praveen.bakingapp.ui.fragments.RecipeStepDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepDetailActivity extends AppCompatActivity implements RecipeStepDetailFragment.OnFragmentInteractionListener {

    private static final String STEP_EXTRA = "step_extra";
    private static final String POSITION_EXTRA = "position_extra";

    public static Intent newIntent(Context context, List<Step> steps, int position) {
        Intent intent = new Intent(context, RecipeStepDetailActivity.class);
        intent.putParcelableArrayListExtra(STEP_EXTRA, (ArrayList<? extends Parcelable>) steps);
        intent.putExtra(POSITION_EXTRA, position);
        return intent;
    }

    private List<Step> mSteps;
    private int mPosition;
    private ActivityStepDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_details);
        mSteps = getIntent().getParcelableArrayListExtra(STEP_EXTRA);
        mPosition = getIntent().getIntExtra(POSITION_EXTRA, 0);

        setTitleAndAddFragment();
        mBinding.imvLeftNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition--;
                doSomethingOnPosition();
                setTitleAndReplaceFragment();
            }
        });

        mBinding.imvRightNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition++;
                doSomethingOnPosition();
                setTitleAndReplaceFragment();
            }
        });

    }


    private void doSomethingOnPosition() {
        if (mPosition == 0) {
            mBinding.imvLeftNavigation.setVisibility(View.INVISIBLE);
            mBinding.imvRightNavigation.setVisibility(View.VISIBLE);
        } else if (mPosition == mSteps.size() - 1) {
            mBinding.imvRightNavigation.setVisibility(View.INVISIBLE);
            mBinding.imvLeftNavigation.setVisibility(View.VISIBLE);
        } else {
            mBinding.imvLeftNavigation.setVisibility(View.VISIBLE);
            mBinding.imvRightNavigation.setVisibility(View.VISIBLE);
        }
    }

    private void setTitleAndAddFragment() {
        // Add Fragment
        RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(mSteps, mPosition);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_detail_container);
        //Check if fragment already attached to activity
        if (fragment == null) {
            doSomethingOnPosition();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_detail_container, recipeStepDetailFragment)
                    .commit();
        }
    }

    private void setTitleAndReplaceFragment() {
        // Replace Fragment
        RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(mSteps, mPosition);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_detail_container, recipeStepDetailFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
